package com.zlz.e_commerce.service.impl;

import com.google.gson.Gson;
import com.zlz.e_commerce.dao.ProductMapper;
import com.zlz.e_commerce.enums.ProductStatusEnum;
import com.zlz.e_commerce.enums.ResponseEnum;
import com.zlz.e_commerce.form.CartAddForm;
import com.zlz.e_commerce.form.CartUpdateForm;
import com.zlz.e_commerce.pojo.Cart;
import com.zlz.e_commerce.pojo.Product;
import com.zlz.e_commerce.service.ICartService;
import com.zlz.e_commerce.vo.CartProductVo;
import com.zlz.e_commerce.vo.CartVo;
import com.zlz.e_commerce.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements ICartService {

    private static final String CART_REDIS_KEY_TEMPLATE = "cart_%d";

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    Gson gson = new Gson();

    @Override
    public ResponseVo<CartVo> add(Integer uid, CartAddForm cartAddForm) {

        Integer quantity = 1;

        Product product = productMapper.selectByPrimaryKey(cartAddForm.getProductId());

        //商品是否存在
        if (product == null) {
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST);
        }

        //商品是否正常在售
        if (!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())) {
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }

        //商品库存是否充足
        if (product.getStock() <= 0) {
            return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_ERROR);
        }

        //写入到redis
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Cart cart;
        String value = opsForHash.get(redisKey, String.valueOf(product.getId()));

        if (StringUtils.isEmpty(value)) {
            //没有该商品， 新增
            cart = new Cart(product.getId(), quantity, cartAddForm.getSelected());
        } else {
            //已经有了，数量+1
            cart = gson.fromJson(value, Cart.class);
            cart.setQuantity(cart.getQuantity()+1);
        }

        opsForHash.put(redisKey, String.valueOf(product.getId()),
                gson.toJson(cart));


        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> list(Integer uid) {

        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Map<String, String> entries = opsForHash.entries(redisKey);

        Boolean selectedAll = true;
        Integer cartTotalQuantity = 0;
        BigDecimal cartTotalPrice = BigDecimal.ZERO;
        CartVo cartVo = new CartVo();
        List<CartProductVo> cartProductVoList = new ArrayList<>();
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            Integer productId = Integer.valueOf(entry.getKey());
            Cart cart = gson.fromJson(entry.getValue(), Cart.class);
            //TODO 需要优化，使用mysql里的in
            Product product = productMapper.selectByPrimaryKey(productId);
            if (product != null) {
                CartProductVo cartProductVo = new CartProductVo(productId, cart.getQuantity()
                        , product.getName()
                        , product.getSubtitle()
                        , product.getMainImage()
                        , product.getPrice()
                        , product.getStatus()
                        , product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity()))
                        , product.getStock()
                        , cart.getProductSelected()
                        );
                cartProductVoList.add(cartProductVo);

                if (!cart.getProductSelected()) {
                    selectedAll = false;
                }
                //计算总价，只计算选中的
                if (cart.getProductSelected()) {
                    cartTotalPrice = cartTotalPrice.add(cartProductVo.getProductTotalPrice());
                }
            }

            cartTotalQuantity += cart.getQuantity();
        }
        cartVo.setSelectedAll(selectedAll);
        cartVo.setCartTotalQuantity(cartTotalQuantity);
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartProductVoList(cartProductVoList);
        return ResponseVo.success(cartVo);
    }

    @Override
    public ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm) {

        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Cart cart;
        String value = opsForHash.get(redisKey, String.valueOf(productId));

        if (StringUtils.isEmpty(value)) {
            //没有该商品，报错
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }
        //已经有了，修改内容
        cart = gson.fromJson(value, Cart.class);
        if (cartUpdateForm.getQuantity() != null && cartUpdateForm.getQuantity() >= 0) {
            cart.setQuantity(cartUpdateForm.getQuantity());
        }
        if (cartUpdateForm.getSelected() != null) {
            cart.setProductSelected(cartUpdateForm.getSelected());
        }

        opsForHash.put(redisKey, String.valueOf(productId), gson.toJson(cart));

        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> delete(Integer uid, Integer productId) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Cart cart;
        String value = opsForHash.get(redisKey, String.valueOf(productId));

        if (StringUtils.isEmpty(value)) {
            //没有该商品，报错
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }

        opsForHash.delete(redisKey, String.valueOf(productId));
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> selectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        List<Cart> cartList = listForCart(uid);
        for (Cart cart : cartList) {
            cart.setProductSelected(true);
            opsForHash.put(redisKey, String.valueOf(cart.getProductId()), gson.toJson(cart));
        }
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> unSelectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        List<Cart> cartList = listForCart(uid);
        for (Cart cart : cartList) {
            cart.setProductSelected(false);
            opsForHash.put(redisKey, String.valueOf(cart.getProductId()), gson.toJson(cart));
        }
        return list(uid);
    }

    @Override
    public ResponseVo<Integer> sum(Integer uid) {
        Integer sum = listForCart(uid).stream().map(Cart::getQuantity).reduce(0, Integer::sum);
        return ResponseVo.success(sum);
    }

    public List<Cart> listForCart(Integer uid){
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Map<String, String> entries = opsForHash.entries(redisKey);

        List<Cart> cartList = new ArrayList<>();
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            cartList.add(gson.fromJson(entry.getValue(), Cart.class));
        }
        return cartList;
    }

}

package com.study.jpa.chap01_basic.repository;

import com.study.jpa.chap01_basic.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false) // 실무에서는 true, 지금은 db에 저장되는지 확인 위해서 false
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @BeforeEach // 테스트 돌리기 전에 실행
    void insertDummyData() {
        //given
        Product p1 = Product.builder()
                .name("아이폰")
                .category(Product.Category.ELECTRONIC)
                .price(1000000)
                .build();
        Product p2 = Product.builder()
                .name("탕수육")
                .category(Product.Category.FOOD)
                .price(20000)
                .build();
        Product p3 = Product.builder()
                .name("구두")
                .category(Product.Category.FASHION)
                .price(300000)
                .build();
        Product p4 = Product.builder()
                .name("쓰레기")
                .category(Product.Category.FOOD)
                .build();

        //when
        Product saved1 = productRepository.save(p1);
        Product saved2 = productRepository.save(p2);
        Product saved3 = productRepository.save(p3);
        Product saved4 = productRepository.save(p4);

    }
    
    @Test
    @DisplayName("5번째 상품을 데이터베이스에 저장해야한다")
    void testSave() {
        //given
        Product product = Product.builder()
                .name("정장")
                .category(Product.Category.FASHION)
                .price(1200000)
                .build();

        Product saved = productRepository.save(product);

        assertNotNull(saved);
    }

    @Test
    @DisplayName("id가 2번인 데이터를 삭제해야 한다")
    void testRemove() {
        //given
        long id = 2L;
        //when
        productRepository.deleteById(id);
        //then
    }

    @Test
    @DisplayName("상품 전체조회를 하면 상품의 개수가 4개여야 한다.")
    void testFindAll() {
        //given

        //when
        List<Product> products = productRepository.findAll();
        //then
        products.forEach(System.out::println);

        assertEquals(4, products.size());
    }

    @Test
    @DisplayName("3번 상품을 조회하면 '구두'여야 한다")
    void testFindOne() {
        //given
        Long id = 3L;
        //when
        Optional<Product> product = productRepository.findById(id);
        /*Optional - null 안전성 때문에*/
        //then
        product.ifPresent(p -> {
            assertEquals("구두", p.getName());
        });

        Product foundProduct = product.get();
        assertNotNull(foundProduct);

        System.out.println("\nfoundProduct = " + foundProduct);
    }

    @Test
    @DisplayName("2번 상품의 이름과 가결을 변경해야 한다")
    void testModify() {
        //given
        long id = 2L;
        String newName = "칠리새우";
        int newPrice = 50000;
        //when
        /* jpa에서 update는 따로 메서드를 지원하지 않고, 조회한 후 setter로 변경하면 자동으로 update문이 나감*/
        /* 변경 후 다시 save를 호출하기! */
        Optional<Product> product = productRepository.findById(id);
        product.ifPresent(p -> {
            p.setName(newName);
            p.setPrice(newPrice);

            productRepository.save(p);
        });
        //then
        assertTrue(product.isPresent());

        Product p = product.get();
        assertEquals("칠리새우", p.getName());
    }
    
}
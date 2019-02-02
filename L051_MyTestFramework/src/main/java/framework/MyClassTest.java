package framework;

import annotations.*;

public class MyClassTest {

    public MyClassTest(){
        System.out.println("constructor");
    }

    @BeforeAll
    public void beforeAll(){
        System.out.println("beforeAll");
    }
    @AfterAll
    public void afterAll(){
        System.out.println("afterAll");
    }
    @BeforeEach
    public void beforeEach(){
        System.out.println("beforeEach");
    }
    @BeforeEach
    public void beforeEach2(){
        System.out.println("beforeEach2");
    }
    @AfterEach
    public void afterEach(){
        System.out.println("afterEach");
    }
    @Test
    public void test1(){
        System.out.println("test1");
    }
    @Test
    public void test2(){
        System.out.println("test2");
    }

}

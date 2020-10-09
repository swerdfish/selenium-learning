package junit.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Demo01_JUnitAnnotations {
	
	@BeforeAll
	public static void beforeClass() {
		System.out.println("Inside @BeforeAll (formerly @BeforeClass)");
	}
	
	@BeforeEach
	public void before() {
		System.out.println("Inside @BeforeEach (formerly @Before)");
	}

	@Test
	void test() {
		System.out.println("Inside test method");
	}
	
	@AfterEach
	public void after() {
		System.out.println("Inside @AfterEach (formerly @After)");
	}
	
	@AfterAll
	public static void afterClass() {
		System.out.println("Inside @AfterAll (formerly @AfterClass");
	}
	
}

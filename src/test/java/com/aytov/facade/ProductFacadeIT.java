package com.aytov.facade;

import com.aytov.ProductDemoApplication;
import com.aytov.facade.dto.MoneyDto;
import com.aytov.facade.dto.ProductDto;
import com.aytov.facade.dto.ProductTagDto;
import com.aytov.facade.dto.ProductsDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebIntegrationTest({"management.port=0"})
@SpringApplicationConfiguration(classes = ProductDemoApplication.class)
@Transactional
public class ProductFacadeIT {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Value("${local.server.port}")
    private int port;

    @Rule
    public RestDocumentation restDocumentation = new RestDocumentation("target/generated-snippets");

    private String base;
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/products/").toString();
        mvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

    @Test
    public void testGetAllProducts() throws Exception {
        ProductDto product = createProduct();

        mvc.perform(MockMvcRequestBuilders.post(base).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(product))).andExpect(status().isCreated());

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(base).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(document("getAllProducts")).andReturn();

        String productsStr = mvcResult.getResponse().getContentAsString();
        ProductsDto products = objectMapper.readValue(productsStr, ProductsDto.class);

        assertTrue(products.getList().size() == 1);
        assertProductEquals(product, products.getList().get(0));
    }

    @Test
    public void testCreateProduct() throws Exception {
        ProductDto product = createProduct();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(base).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(product))).andExpect(status().isCreated()).andDo(document("createProduct")).andReturn();

        String productStr = mvcResult.getResponse().getContentAsString();
        ProductDto createdProduct = objectMapper.readValue(productStr, ProductDto.class);

        assertProductEquals(product, createdProduct);
    }

    @Test
    public void testUpdateProduct() throws Exception {
        ProductDto product = createProduct();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(base).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(product))).andExpect(status().isCreated()).andReturn();

        String productStr = mvcResult.getResponse().getContentAsString();
        product = objectMapper.readValue(productStr, ProductDto.class);

        product.setName("UpdateName");
        MoneyDto price = new MoneyDto();
        price.setCurrencyCode("GBP");
        price.setValue(new BigDecimal(3.5));
        product.setPrice(price);
        product.setDescription("UpdateDescription");
        ProductTagDto tag1 = new ProductTagDto();
        tag1.setName("NewTag1");
        ProductTagDto tag2 = new ProductTagDto();
        tag2.setName("NewTag2");
        List<ProductTagDto> tags = new ArrayList<>(2);
        tags.add(tag1);
        tags.add(tag2);
        product.setTags(tags);
        product.setPricePoints(Collections.emptyList());

        mvcResult = mvc.perform(MockMvcRequestBuilders.put(base + product.getId()).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(product))).andExpect(status().isOk()).andDo(document("updateProduct")).andReturn();

        productStr = mvcResult.getResponse().getContentAsString();
        ProductDto returnedProduct = objectMapper.readValue(productStr, ProductDto.class);

        assertProductEquals(product, returnedProduct);
    }

    @Test
    public void testGetProduct() throws Exception {
        ProductDto product = createProduct();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(base).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(product))).andExpect(status().isCreated()).andReturn();

        String productStr = mvcResult.getResponse().getContentAsString();
        product = objectMapper.readValue(productStr, ProductDto.class);

        mvcResult = mvc.perform(MockMvcRequestBuilders.get(base + product.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(document("getProduct")).andReturn();

        productStr = mvcResult.getResponse().getContentAsString();
        ProductDto returnedProduct = objectMapper.readValue(productStr, ProductDto.class);

        assertProductEquals(product, returnedProduct);
    }

    @Test
    public void testUpdateProductPrice() throws Exception {
        ProductDto product = createProduct();

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(base).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(product))).andExpect(status().isCreated()).andReturn();

        String productStr = mvcResult.getResponse().getContentAsString();
        product = objectMapper.readValue(productStr, ProductDto.class);

        MoneyDto price = new MoneyDto();
        price.setCurrencyCode("GBP");
        price.setValue(new BigDecimal(3.5));
        product.setPrice(price);
        product.setPricePoints(Collections.emptyList());

        mvcResult = mvc.perform(MockMvcRequestBuilders.put(base + product.getId()).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(product))).andExpect(status().isOk()).andDo(document("updateProductPrice")).andReturn();

        productStr = mvcResult.getResponse().getContentAsString();
        ProductDto returnedProduct = objectMapper.readValue(productStr, ProductDto.class);

        assertProductEquals(product, returnedProduct);
    }

    private ProductDto createProduct() {
        ProductDto product = new ProductDto();
        product.setName("TestName");
        MoneyDto price = new MoneyDto();
        price.setCurrencyCode("USD");
        price.setValue(new BigDecimal(3.3));
        product.setPrice(price);
        product.setDescription("TestDescription");
        ProductTagDto tag1 = new ProductTagDto();
        tag1.setName("TestTag");
        ProductTagDto tag2 = new ProductTagDto();
        tag2.setName("NewTag");
        List<ProductTagDto> tags = new ArrayList<>(2);
        tags.add(tag1);
        tags.add(tag2);
        product.setTags(tags);
        product.setPricePoints(Collections.EMPTY_LIST);

        return product;
    }

    private static void assertProductEquals(ProductDto product1, ProductDto prodcut2) {
        assertEquals(prodcut2, product1);
    }
}
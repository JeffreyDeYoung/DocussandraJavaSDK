/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.docussandra.javasdk.dao.impl;

import com.docussandra.javasdk.Config;
import com.docussandra.javasdk.dao.QueryDao;
import com.docussandra.javasdk.testhelper.TestUtils;
import com.pearson.docussandra.domain.objects.Query;
import com.pearson.docussandra.domain.objects.QueryResponseWrapper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author https://github.com/JeffreyDeYoung
 */
public class QueryDaoImplTest
{
    
    private Config config;
    private QueryDao instance;

    public QueryDaoImplTest()
    {
        config = TestUtils.establishTestServer();
        instance = new QueryDaoImpl(config);
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp() throws InterruptedException
    {
        TestUtils.insertTestDb(config);
        TestUtils.insertTestTable(config);
        TestUtils.insertTestIndex(config);
        Thread.sleep(2000);
    }

    @After
    public void tearDown() throws InterruptedException
    {
        TestUtils.cleanupTestDb(config);
        Thread.sleep(1000);
    }

    /**
     * Test of query method, of class QueryDaoImpl.
     */
    @Test
    public void testQuery_String_Query() throws Exception
    {
        System.out.println("query");
        String db = TestUtils.getTestDb().name();
        Query query = new Query();
        query.setDatabase(db);
        query.setTable(TestUtils.getTestTable().name());
        query.setWhere("test = 'testvalue'");
        QueryResponseWrapper result = instance.query(db, query);
        assertNotNull(result);
    }

    /**
     * Test of query method, of class QueryDaoImpl.
     */
    @Test
    @Ignore
    public void testQuery_4args() throws Exception
    {
        System.out.println("query");
        String db = "";
        Query query = null;
        int limit = 0;
        long offset = 0L;
        QueryDaoImpl instance = null;
        QueryResponseWrapper expResult = null;
        QueryResponseWrapper result = instance.query(db, query, limit, offset);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

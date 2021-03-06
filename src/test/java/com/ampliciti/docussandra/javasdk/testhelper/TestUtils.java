package com.ampliciti.docussandra.javasdk.testhelper;

import com.ampliciti.db.docussandra.javasdk.Config;
import com.ampliciti.db.docussandra.javasdk.dao.IndexDao;
import com.ampliciti.db.docussandra.javasdk.dao.TableDao;
import com.ampliciti.db.docussandra.javasdk.dao.impl.DatabaseDaoImpl;
import com.ampliciti.db.docussandra.javasdk.dao.impl.IndexDaoImpl;
import com.ampliciti.db.docussandra.javasdk.dao.impl.TableDaoImpl;
import com.docussandra.testhelpers.TestDocussandraManager;
import com.pearson.docussandra.domain.objects.Database;
import com.pearson.docussandra.domain.objects.Document;
import com.pearson.docussandra.domain.objects.FieldDataType;
import com.pearson.docussandra.domain.objects.Index;
import com.pearson.docussandra.domain.objects.IndexField;
import com.pearson.docussandra.domain.objects.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Test utility class.
 *
 * @author https://github.com/JeffreyDeYoung
 */
public class TestUtils {

  /**
   * Gets a basic Database object for testing.
   *
   * @return
   */
  public static Database getTestDb() {
    Database entity = new Database("testdb");
    entity.setDescription("This was a db created by java sdk tests.");
    return entity;
  }

  /**
   * Gets a basic Document object for testing.
   *
   * @return
   */
  public static Document getTestDocument() {
    Document entity = new Document();
    entity.setTable(getTestTable());
    entity.setObjectAsString(
        "{\"greeting\":\"hello\", \"myindexedfield\": \"thisismyfield\", \"myindexedfield1\":\"my second field\", \"myindexedfield2\":\"my third field\"}");
    entity.setUuid(new UUID(0L, 1L));
    entity.setCreatedAt(new Date());
    entity.setUpdatedAt(new Date());
    return entity;
  }

  /**
   * Gets another basic Document object for testing.
   *
   * @return
   */
  public static Document getTestDocument2() {
    Document entity = new Document();
    entity.setTable(getTestTable());
    entity.setObjectAsString(
        "{\"greeting\":\"hey\", \"myindexedfield\": \"thisismyfieldagain\", \"myindexedfield1\":\"my second field again\", \"myindexedfield2\":\"my third field yet again\"}");
    entity.setUuid(new UUID(0L, 1L));
    entity.setCreatedAt(new Date());
    entity.setUpdatedAt(new Date());
    return entity;
  }

  /**
   * Cleans up the test Database object that may or may not have been created.
   *
   * @param config Config object with information on how to connect to the database.
   */
  public static void cleanupTestDb(Config config) {
    try {
      DatabaseDaoImpl instance = new DatabaseDaoImpl(config);
      instance.delete(TestUtils.getTestDb());
    } catch (Exception e) {
      ; // don't care
    }
  }

  /**
   * Creates and inserts a test db.
   *
   * @param config Config object with information on how to connect to the database.
   */
  public static void insertTestDb(Config config) {
    try {
      DatabaseDaoImpl instance = new DatabaseDaoImpl(config);
      instance.create(TestUtils.getTestDb());
    } catch (Exception e) {
      throw new RuntimeException(e); // it's just a test
    }
  }

  public static Table getTestTable() {
    Table entity = new Table();
    entity.setName("testtable");
    entity.setDescription("This was a table created by java sdk tests.");
    entity.setDatabaseByObject(TestUtils.getTestDb());
    return entity;
  }

  /**
   * Creates and inserts a test table.
   *
   * @param config Config object with information on how to connect to the database.
   */
  public static void insertTestTable(Config config) {
    try {
      TableDao instance = new TableDaoImpl(config);
      instance.create(TestUtils.getTestTable());
    } catch (Exception e) {
      throw new RuntimeException(e); // it's just a test
    }
  }

  public static void cleanupTestTable(Config config) {
    try {
      TableDaoImpl tableImplInstance = new TableDaoImpl(config);
      tableImplInstance.delete(TestUtils.getTestTable());
    } catch (Exception e) {
      ; // don't care
    }
  }

  public static Index getTestIndex() {
    Index entity = new Index();
    entity.setName("testindex");
    entity.setTable(TestUtils.getTestTable());
    List<IndexField> fields = new ArrayList<>();
    fields.add(new IndexField("test", FieldDataType.TEXT));
    fields.add(new IndexField("test1", FieldDataType.INTEGER));
    entity.setFields(fields);
    return entity;
  }

  /**
   * Creates and inserts a test index.
   *
   * @param config Config object with information on how to connect to the database.
   */
  public static void insertTestIndex(Config config) {
    try {
      IndexDao instance = new IndexDaoImpl(config);
      instance.create(TestUtils.getTestIndex());
    } catch (Exception e) {
      throw new RuntimeException(e); // it's just a test
    }
  }

  /**
   * Establishes a test Docussandra server and returns a Config object for it.
   *
   * @return A config object for the test Docussandra server that can be used to connect.
   */
  public static Config establishTestServer() {
    // String cassandraKeyspace = "docussandra";
    try {
      TestDocussandraManager.getManager().ensureTestDocussandraRunning(true);
    } catch (Exception e) {
      throw new RuntimeException("Problem establishing test Docussandra", e);// generally, we don't
                                                                             // want to throw a
                                                                             // runtime exception
                                                                             // like this, however,
                                                                             // this is just for
                                                                             // testing
    }
    return new Config("http://localhost:19080/");
  }

}

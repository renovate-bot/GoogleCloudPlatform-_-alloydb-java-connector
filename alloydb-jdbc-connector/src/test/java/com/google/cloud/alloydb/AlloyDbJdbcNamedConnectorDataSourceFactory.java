/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.cloud.alloydb;

// [START alloydb_hikaricp_connect_connector_named]

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class AlloyDbJdbcNamedConnectorDataSourceFactory {

  public static final String ALLOYDB_DB = System.getenv("ALLOYDB_DB");
  public static final String ALLOYDB_USER = System.getenv("ALLOYDB_USER");
  public static final String ALLOYDB_PASS = System.getenv("ALLOYDB_PASS");
  public static final String ALLOYDB_INSTANCE_NAME = System.getenv("ALLOYDB_INSTANCE_NAME");
  public static final String ALLOYDB_IMPERSONATED_USER = System.getenv("ALLOYDB_IMPERSONATED_USER");

  static HikariDataSource createDataSource() {

    // Register a named Connector
    ConnectorConfig namedConnectorConfig =
        new ConnectorConfig.Builder().withRefreshStrategy(RefreshStrategy.LAZY).build();
    ConnectorRegistry.register("my-connector", namedConnectorConfig);

    HikariConfig config = new HikariConfig();

    config.setJdbcUrl(String.format("jdbc:postgresql:///%s", ALLOYDB_DB));
    config.setUsername(ALLOYDB_USER); // e.g., "postgres"
    config.setPassword(ALLOYDB_PASS); // e.g., "secret-password"
    config.addDataSourceProperty("socketFactory", "com.google.cloud.alloydb.SocketFactory");
    // e.g., "projects/my-project/locations/us-central1/clusters/my-cluster/instances/my-instance"
    config.addDataSourceProperty("alloydbInstanceName", ALLOYDB_INSTANCE_NAME);
    config.addDataSourceProperty("alloydbNamedConnector", "my-connector");

    return new HikariDataSource(config);
  }
}
// [END alloydb_hikaricp_connect_connector_named]

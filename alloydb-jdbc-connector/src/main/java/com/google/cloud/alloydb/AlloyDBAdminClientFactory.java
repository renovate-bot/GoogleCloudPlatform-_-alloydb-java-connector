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

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.rpc.FixedHeaderProvider;
import com.google.cloud.alloydb.v1alpha.AlloyDBAdminClient;
import com.google.cloud.alloydb.v1alpha.AlloyDBAdminSettings;
import com.google.common.collect.ImmutableMap;
import java.io.IOException;
import java.util.Map;

/**
 * The AlloyDB Admin Client Factory encapsulates configuration of the client and is the single way
 * to create new clients in the Connector.
 */
class AlloyDBAdminClientFactory {

  private static final String DEFAULT_ENDPOINT = "alloydb.googleapis.com:443";

  static AlloyDBAdminClient create(
      FixedCredentialsProvider credentialsProvider, ConnectorConfig config, String userAgents)
      throws IOException {
    AlloyDBAdminSettings.Builder settingsBuilder = AlloyDBAdminSettings.newBuilder();

    String endpoint = config.getAdminServiceEndpoint();
    if (endpoint == null || endpoint.isEmpty()) {
      endpoint = DEFAULT_ENDPOINT;
    }

    Map<String, String> headers =
        ImmutableMap.<String, String>builder().put("user-agent", userAgents).build();

    settingsBuilder
        .setEndpoint(endpoint)
        .setHeaderProvider(FixedHeaderProvider.create(headers))
        .setCredentialsProvider(credentialsProvider);

    if (config.getQuotaProject() != null) {
      settingsBuilder.setQuotaProjectId(config.getQuotaProject());
    }
    return AlloyDBAdminClient.create(settingsBuilder.build());
  }
}

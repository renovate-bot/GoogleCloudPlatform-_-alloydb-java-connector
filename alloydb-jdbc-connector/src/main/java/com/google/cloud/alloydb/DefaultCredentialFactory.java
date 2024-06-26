/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.alloydb;

import com.google.auth.oauth2.GoogleCredentials;
import java.io.IOException;
import java.util.Arrays;

class DefaultCredentialFactory implements CredentialFactory {
  @Override
  public GoogleCredentials getCredentials() {
    GoogleCredentials credentials;
    try {
      credentials = GoogleCredentials.getApplicationDefault();
    } catch (IOException e) {
      throw new RuntimeException("failed to retrieve OAuth2 access token", e);
    }

    if (credentials.createScopedRequired()) {
      credentials = credentials.createScoped(Arrays.asList(SCOPE_CLOUD_PLATFORM));
    }

    return credentials;
  }
}

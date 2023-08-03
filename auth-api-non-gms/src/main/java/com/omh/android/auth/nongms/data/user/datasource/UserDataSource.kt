/*
 * Copyright 2023 Open Mobile Hub
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.omh.android.auth.nongms.data.user.datasource

import com.omh.android.auth.api.models.OmhUserProfile

internal interface UserDataSource {

    /**
     * Handles the ID token returned from the Auth Provider. The operation can't run on the main thread.
     *
     * Once it passes the checks, the data is stored in the Encrypted Shared Preferences.
     *
     * @param idToken -> token to validate and handle.
     * @param clientId -> clientId from console.
     */
    suspend fun handleIdToken(idToken: String, clientId: String)

    /**
     * Checks if there's any relevant data stored for the user. If any of the required values are
     * null, then it's assumed that no user is stored and a null object is returned.
     */
    fun getProfileData(): OmhUserProfile?
}

/*
 * Copyright (c) 2021 Payara Foundation and/or its affiliates. All rights reserved.
 *
 *  The contents of this file are subject to the terms of either the GNU
 *  General Public License Version 2 only ("GPL") or the Common Development
 *  and Distribution License("CDDL") (collectively, the "License").  You
 *  may not use this file except in compliance with the License.  You can
 *  obtain a copy of the License at
 *  https://github.com/payara/Payara/blob/master/LICENSE.txt
 *  See the License for the specific
 *  language governing permissions and limitations under the License.
 *
 *  When distributing the software, include this License Header Notice in each
 *  file and include the License file at glassfish/legal/LICENSE.txt.
 *
 *  GPL Classpath Exception:
 *  The Payara Foundation designates this particular file as subject to the "Classpath"
 *  exception as provided by the Payara Foundation in the GPL Version 2 section of the License
 *  file that accompanied this code.
 *
 *  Modifications:
 *  If applicable, add the following below the License Header, with the fields
 *  enclosed by brackets [] replaced by your own identifying information:
 *  "Portions Copyright [year] [name of copyright owner]"
 *
 *  Contributor(s):
 *  If you wish your version of this file to be governed by only the CDDL or
 *  only the GPL Version 2, indicate your decision by adding "[Contributor]
 *  elects to include this software in this distribution under the [CDDL or GPL
 *  Version 2] license."  If you don't indicate a single choice of license, a
 *  recipient has the option to distribute your version of this file under
 *  either the CDDL, the GPL Version 2 or to extend the choice of license to
 *  its licensees as provided above.  However, if you add GPL Version 2 code
 *  and therefore, elected the GPL Version 2 license, then the option applies
 *  only if the new code is made subject to such option by the copyright
 *  holder.
 */
package fish.payara.security.annotations;

import fish.payara.security.openid.api.DisplayType;
import fish.payara.security.openid.api.PromptType;
import fish.payara.security.openid.api.OpenIdConstant;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * {@link OpenIdAuthenticationDefinition} annotation defines openid connect
 * client configuration and The value of each parameter can be overwritten via
 * mp config properties.
 *
 * @author Gaurav Gupta
 */
@Target({TYPE, METHOD})
@Retention(RUNTIME)
public @interface OpenIdAuthenticationDefinition {

    /**
     * Required. The provider uri (
     * http://openid.net/specs/openid-connect-discovery-1_0.html ) to read /
     * discover the metadata of the openid provider.
     *
     * @return
     */
    String providerURI() default "";

    /**
     * To override the openid connect provider's metadata property discovered
     * via providerUri.
     *
     * @return
     */
    OpenIdProviderMetadata providerMetadata() default @OpenIdProviderMetadata;

    /**
     * Optional. The claims definition defines the custom claims mapping of
     * caller name and groups.
     *
     * @return
     */
    ClaimsDefinition claimsDefinition() default @ClaimsDefinition;

    /**
     * Optional. The Logout definition defines the logout and RP session
     * management configuration.
     *
     * @return
     */
    LogoutDefinition logout() default @LogoutDefinition;

    /**
     * Required. The client identifier issued when the application was
     * registered
     * <p>
     * To set this using Microprofile Config use
     * {@code payara.security.openid.cliendId}
     *
     * @return the client identifier
     */
    String clientId() default "";

    /**
     * Required. The client secret
     * <p>
     * It is recommended to set this using an alias.
     * </p>
     * To set this using Microprofile Config use
     * {@code payara.security.openid.clientSecret}
     *
     * @return
     * @see
     * <a href="https://docs.payara.fish/documentation/payara-server/password-aliases/password-aliases-overview.html">Payara
     * Password Aliases Documentation</a>
     */
    String clientSecret() default "";

    /**
     * The redirect URI to which the response will be sent by OpenId Connect
     * Provider. This URI must exactly match one of the Redirection URI values
     * for the Client pre-registered at the OpenID Provider.
     *
     * To set this using Microprofile Config use
     * {@code payara.security.openid.redirectURI}
     *
     * @return
     */
    String redirectURI() default "${baseURL}/Callback";

    /**
     * Optional. The scope value defines the access privileges. The basic (and
     * required) scope for OpenID Connect is the openid scope.
     *
     * @return
     */
    String[] scope() default {OpenIdConstant.OPENID_SCOPE, OpenIdConstant.EMAIL_SCOPE, OpenIdConstant.PROFILE_SCOPE};

    /**
     * Optional. Response Type value defines the processing flow to be used. By
     * default, the value is code (Authorization Code Flow).
     *
     * @return
     */
    String responseType() default "code";

    /**
     * Optional. Informs the Authorization Server of the mechanism to be used
     * for returning parameters from the Authorization Endpoint.
     *
     * @return
     */
    String responseMode() default "";

    /**
     * Optional. The prompt value specifies whether the authorization server
     * prompts the user for reauthentication and consent. If no value is
     * specified and the user has not previously authorized access, then the
     * user is shown a consent screen.
     *
     * @return
     */
    PromptType[] prompt() default {};

    /**
     * Optional. The display value specifying how the authorization server
     * displays the authentication and consent user interface pages.
     *
     * @return
     */
    DisplayType display() default DisplayType.PAGE;

    /**
     * Optional. Enables string value used to mitigate replay attacks.
     *
     * @return
     */
    boolean useNonce() default true;

    /**
     * Optional. If enabled state & nonce value stored in session otherwise in
     * cookies.
     *
     * @return
     */
    boolean useSession() default true;

    /**
     * An array of extra options that will be sent to the OAuth provider.
     * <p>
     * These must be in the form of {@code "key=value"} i.e.
     * <code> extraParameters={"key1=value", "key2=value2"} </code>
     *
     * To set this using Microprofile Config use {@code payara.security.openid.extraParams.raw}, in URL query format:
     * {@code key=value&key2=value+with+spaces}. The keys may repeat.
     *
     * @return
     */
    String[] extraParameters() default {};

    /**
     * Optional. Sets the connect timeout(in milliseconds) for Remote JWKS
     * retrieval. Value must not be negative and if value is zero then infinite
     * timeout.
     *
     * @return
     */
    int jwksConnectTimeout() default 500;

    /**
     * Optional. Sets the read timeout(in milliseconds) for Remote JWKS
     * retrieval. Value must not be negative and if value is zero then infinite
     * timeout.
     *
     * @return
     */
    int jwksReadTimeout() default 500;

    /**
     * Optional. Enables or disables the automatically performed refresh of
     * Access and Refresh Token.
     *
     * @return {@code true}, if Access and Refresh Token shall be refreshed
     * automatically when they are expired.
     */
    boolean tokenAutoRefresh() default false;

    /**
     * Optional. Sets the minimum validity time in milliseconds the Access Token
     * must be valid before it is considered expired. Value must not be
     * negative.
     *
     * @return
     */
    int tokenMinValidity() default 10 * 1000;

    /**
     * Optional. Indicates to skip the /userinfo endpoint call and get the user information from ID Token.
     *
     * @return
     */
    boolean userClaimsFromIDToken() default false;

    /**
     * The Microprofile Config key for the provider uri is <code>{@value}</code>
     */
    String OPENID_MP_PROVIDER_URI = "payara.security.openid.providerURI";

    /**
     * The Microprofile Config key for the clientId is <code>{@value}</code>
     */
    String OPENID_MP_CLIENT_ID = "payara.security.openid.clientId";

    /**
     * The Microprofile Config key for the client secret is
     * <code>{@value}</code>
     */
    String OPENID_MP_CLIENT_SECRET = "payara.security.openid.clientSecret";

    /**
     * The Microprofile Config key for the redirect URI is <code>{@value}</code>
     */
    String OPENID_MP_REDIRECT_URI = "payara.security.openid.redirectURI";

    /**
     * The Microprofile Config key for the scope is <code>{@value}</code>
     *
     * <p>
     * The defined values are: profile, email, address, phone, and
     * offline_access.
     * </p>
     */
    String OPENID_MP_SCOPE = "payara.security.openid.scope";

    /**
     * The Microprofile Config key for the scope is <code>{@value}</code>
     *
     * <p>
     * The defined values are: profile, email, address, phone, and
     * offline_access.
     * </p>
     */
    String OPENID_MP_RESPONSE_TYPE = "payara.security.openid.responseType";

    /**
     * The Microprofile Config key for the responseMode is <code>{@value}</code>
     */
    String OPENID_MP_RESPONSE_MODE = "payara.security.openid.responseMode";

    /**
     * The Microprofile Config key for the prompt is <code>{@value}</code>.
     *
     * <p>
     * Value is case sensitive and multiple values must be separated by space
     * delimiter. The defined values are: none, login, consent, select_account.
     * If this parameter contains 'none' with any other value, an error is
     * returned.
     * </p>
     *
     */
    String OPENID_MP_PROMPT = "payara.security.openid.prompt";

    /**
     * The Microprofile Config key for the display is <code>{@value}</code>.
     *
     * <p>
     * The defined values are: page, popup, touch, and wap. If the display
     * parameter is not specified then 'page' is the default display mode.
     * </p>
     *
     */
    String OPENID_MP_DISPLAY = "payara.security.openid.display";

    /**
     * The Microprofile Config key for the nonce is <code>{@value}</code>.
     */
    String OPENID_MP_USE_NONCE = "payara.security.openid.useNonce";

    /**
     * The Microprofile Config key to enable the session is
     * <code>{@value}</code>.
     */
    String OPENID_MP_USE_SESSION = "payara.security.openid.useSession";

    /**
     * The Microprofile Config key for the connect timeout of JWKS retrieval is
     * <code>{@value}</code>.
     */
    String OPENID_MP_JWKS_CONNECT_TIMEOUT = "payara.security.openid.jwks.connect.timeout";

    /**
     * The Microprofile Config key for the read timeout of JWKS retrieval is
     * <code>{@value}</code>.
     */
    String OPENID_MP_JWKS_READ_TIMEOUT = "payara.security.openid.jwks.read.timeout";

    /**
     * The Microprofile Config key for the encryption algorithm is
     * <code>{@value}</code>.
     */
    String OPENID_MP_CLIENT_ENC_ALGORITHM = "payara.security.openid.client.encryption.algorithm";

    /**
     * The Microprofile Config key for the encryption method is
     * <code>{@value}</code>.
     */
    String OPENID_MP_CLIENT_ENC_METHOD = "payara.security.openid.client.encryption.method";

    /**
     * The Microprofile Config key for the private key jwks is
     * <code>{@value}</code>.
     */
    String OPENID_MP_CLIENT_ENC_JWKS = "payara.security.openid.client.encryption.jwks";

    /**
     * The Microprofile Config key for the Access Token auto refresh is
     * <code>{@value}</code>.
     */
    String OPENID_MP_TOKEN_AUTO_REFRESH = "payara.security.openid.token.autoRefresh";

    /**
     * The Microprofile Config key for the minimum validity in secondes of
     * Access Tokens is <code>{@value}</code>.
     */
    String OPENID_MP_TOKEN_MIN_VALIDITY = "payara.security.openid.token.minValidity";

    /**
     * The Microprofile Config key for evaluating EL expressions for every HTTP session is <code>{@value}</code>
     */
    String OPENID_MP_SESSION_SCOPED_CONFIGURATION = "payara.security.openid.sessionScopedConfiguration";

    /**
     * The Microprofile Config key to skip the /userinfo endpoint call
     * and get the user information from ID Token is <code>{@value}</code>
     */
    String OPENID_MP_USER_CLAIMS_FROM_ID_TOKEN = "payara.security.openid.userClaimsFromIDToken";

    /**
     * The Microprofile Config key for extraParams is <code>{@value}</code>. Use URL query format to store key/value
     * pairs: {@code key=value&key2=value+with+spaces}. The keys may repeat.
     */
    String OPENID_MP_EXTRA_PARAMS_RAW = "payara.security.openid.extraParams.raw";

    /**
     * The Microprofile Config key to disable scope validation is <code>{@value}</code>
     */
    String OPENID_MP_DISABLE_SCOPE_VALIDATION = "payara.security.openid.disableScopeValidation";
}

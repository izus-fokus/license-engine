package resus.licenseengine.fossology.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

public class TokenRequest {

    public TokenRequest() {}

    public TokenRequest(String username, String password, String token_name, String token_expire, TokenScopeEnum token_scope) {
        this.username = username;
        this.password = password;
        this.token_name = token_name;
        this.token_expire = token_expire;
        this.token_scope = token_scope;
    }

    @Schema(description = "Username of the login user.")
    /**
     * Username of the login user.
     **/
    private String username = null;

    @Schema(description = "Password of the user trying to login.")
    /**
     * Password of the user trying to login.
     **/
    private String password = null;

    @Schema(description = "Friendly name of the token")
    /**
     * Friendly name of the token
     **/
    private String token_name = null;

    public enum TokenScopeEnum {
        READ("read"),
        WRITE("write");

        private String value;

        TokenScopeEnum(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static TokenScopeEnum fromValue(String text) {
            for (TokenScopeEnum b : TokenScopeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    @Schema(description = "The scope of the token.")
    /**
     * The scope of the token.
     **/
    private TokenScopeEnum token_scope = null;

    @Schema(description = "Date when the token must expire (default max 30 days).")
    /**
     * Date when the token must expire (default max 30 days).
     **/
    private String token_expire = null;

    /**
     * Username of the login user.
     *
     * @return username
     **/
    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public TokenRequest username(String username) {
        this.username = username;
        return this;
    }

    /**
     * Password of the user trying to login.
     *
     * @return password
     **/
    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TokenRequest password(String password) {
        this.password = password;
        return this;
    }

    /**
     * Friendly name of the token
     *
     * @return tokenName
     **/
    @JsonProperty("token_name")
    public String getToken_name() {
        return token_name;
    }

    public void setToken_name(String token_name) {
        this.token_name = token_name;
    }

    public TokenRequest tokenName(String tokenName) {
        this.token_name = tokenName;
        return this;
    }

    /**
     * The scope of the token.
     *
     * @return tokenScope
     **/
    @JsonProperty("token_scope")
    public String getToken_scope() {
        if (token_scope == null) {
            return null;
        }
        return token_scope.getValue();
    }

    public void setToken_scope(TokenScopeEnum token_scope) {
        this.token_scope = token_scope;
    }

    public TokenRequest tokenScope(TokenScopeEnum tokenScope) {
        this.token_scope = tokenScope;
        return this;
    }

    /**
     * Date when the token must expire (default max 30 days).
     *
     * @return tokenExpire
     **/
    @JsonProperty("token_expire")
    public String getToken_expire() {
        return token_expire;
    }

    public void setToken_expire(String token_expire) {
        this.token_expire = token_expire;
    }

    public TokenRequest tokenExpire(String tokenExpire) {
        this.token_expire = tokenExpire;
        return this;
    }


    @Override
    public String toString() {

        return "class TokenRequest {\n" +
                "    username: " + toIndentedString(username) + "\n" +
                "    password: " + toIndentedString(password) + "\n" +
                "    token_name: " + toIndentedString(token_name) + "\n" +
                "    token_scope: " + toIndentedString(token_scope) + "\n" +
                "    token_expire: " + toIndentedString(token_expire) + "\n" +
                "}";
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private static String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

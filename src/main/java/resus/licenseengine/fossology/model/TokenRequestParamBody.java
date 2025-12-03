package resus.licenseengine.fossology.model;

public class TokenRequestParamBody {

    public TokenRequestParamBody() {
    }

    public TokenRequestParamBody(String username, String password, String tokenName, TokenScopeEnum tokenScope, String tokenExpire) {
        this.username = username;
        this.password = password;
        this.tokenName = tokenName;
        this.tokenExpire = tokenExpire;
        this.tokenScope = tokenScope;
    }

    /**
     * Username of the login user.
     **/
    private String username = null;

    /**
     * Password of the user trying to login.
     **/
    private String password = null;

    /**
     * Friendly name of the token
     **/
    private String tokenName = null;

    public enum TokenScopeEnum {
        READ("read"),
        WRITE("write");

        private final String value;

        TokenScopeEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        public static TokenScopeEnum fromValue(String text) {
            for (TokenScopeEnum b : TokenScopeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    /**
     * The scope of the token.
     **/
    private TokenScopeEnum tokenScope = null;

    /**
     * Date when the token must expire (default max 30 days).
     **/
    private String tokenExpire = null;

    /**
     * Username of the login user.
     *
     * @return username
     **/
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Password of the user trying to login.
     *
     * @return password
     **/
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Friendly name of the token
     *
     * @return tokenName
     **/
    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    /**
     * The scope of the token.
     *
     * @return tokenScope
     **/
    public String getTokenScope() {
        if (tokenScope == null) {
            return null;
        }
        return tokenScope.getValue();
    }

    public void setTokenScope(TokenScopeEnum tokenScope) {
        this.tokenScope = tokenScope;
    }

    /**
     * Date when the token must expire (default max 30 days).
     *
     * @return tokenExpire
     **/
    public String getTokenExpire() {
        return tokenExpire;
    }

    public void setTokenExpire(String tokenExpire) {
        this.tokenExpire = tokenExpire;
    }


    @Override
    public String toString() {

        return "username=" + this.username + "&" +
                "password=" + this.password + "&" +
                "tokenName=" + this.tokenName + "&" +
                "tokenScope=" + this.tokenScope + "&" +
                "tokenExpire=" + this.tokenExpire;
    }

}

// ILoginAidlInterface.aidl
package pr.tongson.binder;

// Declare any non-default types here with import statements

interface ILoginAidlInterface {
    void login();
    void loginCallback(boolean loginStatus,String loginUser);
}

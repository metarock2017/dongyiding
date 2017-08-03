public class customer {
    public static void main(String[] args) {
        Factoryhuawei factoryhuawei = new Factoryhuawei();
        huawei huawei = factoryhuawei.createphone();

        Factoryiphone factoryiphone = new Factoryiphone();
        iphone iphone = factoryiphone.createphone();
    }
}
class Customer {
    public static void main(String[] args) {
        factory factory = new factory();
        phone huawei = factory.createphone(1);
        phone iphone = factory.createphone(2);
    }
}

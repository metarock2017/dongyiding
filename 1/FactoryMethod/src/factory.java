interface Factoryphone {
    phone createphone();
}

class Factoryhuawei implements Factoryphone{

    @Override
    public huawei createphone() {

        return new huawei();
    }

}
class Factoryiphone implements Factoryphone {
    @Override
    public iphone createphone() {

        return new iphone();
    }
}

public class factory {
    public phone createphone(int type) {
        switch (type) {

            case 1:
                return new huawei();

            case 2:
                return new iphone();

            default:
                break;
        }
        return null;
    }
}
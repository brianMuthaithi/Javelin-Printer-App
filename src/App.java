public class App {

    public native String GetPAN();
    public native String PrintLine(String embline, String accno);
    public native String ResetDevice(String serial);
    public native String EjectCard();
    public native String GetPrinterStat();
    public native String GetFeederStat();
    public native int GetRibbonCount();
    public native int DeviceErrorClear();

    static {
        try {
            System.load("C:\\Program Files (x86)\\Technote Ltd\\Javelin\\JavelinDll.dll");
            System.load("C:\\Program Files (x86)\\Technote Ltd\\Javelin\\Javelin.dll");
            System.out.println("Lib loaded well");
        } catch (Exception e) {
            System.err.println("Issues with library loading");
        }
    }
   
    public static void main(String[] args) {
        App a = new App();

        //System.out.println(a.DeviceErrorClear());
        System.out.println(a.ResetDevice("0x00000001"));
        System.out.println(a.GetPrinterStat());
        System.out.println(a.GetFeederStat());
        System.out.println(a.GetRibbonCount());
        System.out.println(a.GetPAN());
        System.out.println(a.PrintLine("Brian Kariuki", "0821910700104432"));
        System.out.println(a.EjectCard());
    }
}

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
            System.load("C:\\Windows\\SysWOW64\\JavelinDll.dll");
            System.load("C:\\Windows\\SysWOW64\\Javelin.dll");

            // String workingDir = System.getProperty("user.dir");
            // String dllPath1 = String.valueOf(workingDir) + "\\JavelinDll.dll";
            // String dllPath2 = String.valueOf(workingDir) + "\\Javelin.dll";
            // System.load(dllPath1);
            // System.load(dllPath2);

            // String programFilesPath = System.getenv("ProgramFiles(x86)");
            // if (programFilesPath == null)
            //     programFilesPath = System.getenv("ProgramFiles"); 
            // String dllPath1 = String.valueOf(programFilesPath) + "\\Technote_Ke\\Javelin\\JavelinDll.dll";
            // String dllPath2 = String.valueOf(programFilesPath) + "\\Technote_Ke\\Javelin\\Javelin.dll";
            // System.load(dllPath1);
            // System.load(dllPath2);
        } catch (Exception e) {
            System.out.println("Failed to load dlls");
            System.exit(1);
        }    
    }
}

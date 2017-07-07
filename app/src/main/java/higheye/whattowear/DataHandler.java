package higheye.whattowear;


import android.content.Context;

/**
 * Created by marekk-air13 on 04/06/2017.
 */
public class DataHandler {
    //    private static Double[] listTemp = new Double[50];
    private static String[] listOfEntries = new String[50];
    private static Double[] listTemp = new Double[50];
    private static Double[] listClouds = new Double[50];
    private static Double[] listRain = new Double[50];
    private static Double[] listSnow = new Double[50];
    private static Long[] listDate = new Long[50];
    private static String[] listIcons = new String[50];
    private static Long[] listSunrise = new Long[50];
    private static Long[] listSunset = new Long[50];
    private Context context;


    public void DefineStrings(String input) {
        int index = input.indexOf("\"dt_txt\"");
        int i = 1;
        while (index != -1 && i < 49) {
            listOfEntries[i] = input.substring(0, index + 31);
            input = input.substring(index + 32, input.length());
            i++;
            index = input.indexOf("\"dt_txt\"");
        }
        for (i = 0; i < 49; i++) {
            int startindex;
            int endIndex;
            String cutString;
            if (listOfEntries[i] != null && listOfEntries[i] != "") {
                startindex = listOfEntries[i].indexOf("\"temp\":");
                if (startindex != -1) {
                    cutString = listOfEntries[i].substring(startindex);
                    endIndex = cutString.indexOf(",");
                    listTemp[i] = Double.parseDouble(cutString.substring(7, endIndex));
                }

                startindex = listOfEntries[i].indexOf("\"clouds\":{\"all\":");
                if (startindex != -1) {
                    cutString = listOfEntries[i].substring(startindex);
                    endIndex = cutString.indexOf("}");
                    if (endIndex > 16) {
                        listClouds[i] = Double.parseDouble(cutString.substring(16, endIndex));
                    }
                }
                startindex = listOfEntries[i].indexOf("\"rain\":{\"3h\":");
                if (startindex != -1) {
                    cutString = listOfEntries[i].substring(startindex);
                    endIndex = cutString.indexOf("}");
                    listRain[i] = Double.parseDouble(cutString.substring(13, endIndex));
                }
                startindex = listOfEntries[i].indexOf("\"snow\":{\"3h\":");
                if (startindex != -1) {
                    cutString = listOfEntries[i].substring(startindex);
                    endIndex = cutString.indexOf("}");
                    if (endIndex > 13) {
                        listSnow[i] = Double.parseDouble(cutString.substring(13, endIndex));
//                        listSnow[i] = cutString.substring(13, endIndex);
                    }
                }

                startindex = listOfEntries[i].indexOf("\"dt\":");
                if (startindex != -1) {
                    cutString = listOfEntries[i].substring(startindex);
                    endIndex = cutString.indexOf(",");
                    if (endIndex > 5) {
                        listDate[i] = Long.parseLong(cutString.substring(5, endIndex));
                    }
                }
                // listofentries 0 because only in first row is visible sunrise
                startindex = listOfEntries[0].indexOf("\"sunrise\":");
                if (startindex != -1) {
                    cutString = listOfEntries[0].substring(startindex);
                    endIndex = cutString.indexOf(",");
                    if (endIndex > 10) {
                        listSunrise[i] = Long.parseLong(cutString.substring(10, endIndex));
                    }
                }
                // listofentries 0 because only in first row is visible sunset
                startindex = listOfEntries[0].indexOf("\"sunset\":");
                if (startindex != -1) {
                    cutString = listOfEntries[0].substring(startindex);
                    endIndex = cutString.indexOf("}");
                    if (endIndex > 9) {
                        listSunset[i] = Long.parseLong(cutString.substring(9, endIndex));
                    }
                }

                startindex = listOfEntries[i].indexOf("\"icon\":\"");
                if (startindex != -1) {
                    cutString = listOfEntries[i].substring(startindex);
                    endIndex = cutString.indexOf("}");
                    if (endIndex > 5) {
                        listIcons[i] = cutString.substring(8, endIndex-1);
                    }
                }
            }
        }
    }

    public void setSingleString(String input, int n) {
        listOfEntries[n] = input;
    }

    public Double getSingleTemp(int n) {
        if (listTemp[n] != null) {
            return listTemp[n];
        } else return 0d;
    }

    public Double getSingleCloud(int n) {
        if (listClouds[n] != null) {
            return listClouds[n];
        } else return 0d;
    }

    public Double getSingleRain(int n) {
        if (listRain[n] != null) {
            return listRain[n];
        } else return 0d;
    }

    public Long getSingleDate(int n) {
        if (listDate[n] != null) {
            return listDate[n];
        } else return -9999l;
    }

    public Double getSingleSnow(int n) {
        if (listSnow[n] != null) {
            return listSnow[n];
        } else return 0d;
    }

    public String getSingleIcon(int n) {
        if (listIcons[n] != null) {
            return listIcons[n];
        } else return "no_data";
    }
    public Long getSingleSunrise(int n) {
        if (listSunrise[n] != null) {
            return listSunrise[n];
        } else return -333l;
    }
    public Long getSingleSunset(int n) {
        if (listSunset[n] != null) {
            return listSunset[n];
        } else return -333l;
    }


}
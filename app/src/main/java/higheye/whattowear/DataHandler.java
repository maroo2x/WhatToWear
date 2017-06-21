package higheye.whattowear;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ListView;

import com.google.android.gms.location.LocationRequest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.lang.String;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.R.id.content;
import static android.R.id.input;
import static java.security.AccessController.getContext;

/**
 * Created by marekk-air13 on 04/06/2017.
 */
public class DataHandler {
    //    private static Double[] listTemp = new Double[50];
    public static String[] listTemp = new String[50];
    private static String[] listClouds = new String[50];
    private static String[] listRain = new String[50];
    private static String[] listSnow = new String[50];
    private static Long[] listDate = new Long[50];
    public static String[] listOfEntries = new String[50];

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
                    listTemp[i] = cutString.substring(7, endIndex);
//                    listTemp[i] = cutString.substring(8, endIndex);
                    //                   listTemp[i] = Double.parseDouble(cutString.substring(8, endIndex));
                }

                startindex = listOfEntries[i].indexOf("\"clouds\":{\"all\":");
                if (startindex != -1) {
                    cutString = listOfEntries[i].substring(startindex);
                    endIndex = cutString.indexOf("}");
                    if (endIndex > 16) {
                        //                       listClouds[i] = Double.parseDouble(cutString.substring(17, endIndex));
                        listClouds[i] = cutString.substring(16, endIndex);
                    }
                }
                startindex = listOfEntries[i].indexOf("\"rain\":{\"3h\":");
                if (startindex != -1) {
                    cutString = listOfEntries[i].substring(startindex);
                    endIndex = cutString.indexOf("}");
                    //         listRain[i] = Double.parseDouble(cutString.substring(14, endIndex));
                    listRain[i] = cutString.substring(13, endIndex);
                }
                startindex = listOfEntries[i].indexOf("\"snow\":{\"3h\":");
                if (startindex != -1) {
                    cutString = listOfEntries[i].substring(startindex);
                    endIndex = cutString.indexOf("}");
                    if (endIndex > 13) {
//                        listSnow[i] = Double.parseDouble(cutString.substring(13, endIndex));
                        listSnow[i] = cutString.substring(13, endIndex);
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
            }
        }
    }


    public String getSingleString(int n) {
        return listOfEntries[n];
    }

    public void setSingleString(String input, int n) {
        listOfEntries[n] = input;
    }

    public String getSingleTemp(int n) {
        if (listTemp[n] != null) {
            return listTemp[n];
        } else return "zero";
    }

    public String getSingleCloud(int n) {
        if (listClouds[n] != null) {
            return listClouds[n];
        } else return "zero";
    }

    public String getSingleRain(int n) {
        if (listRain[n] != null) {
            return listRain[n];
        } else return "zero";
    }

    public Long getSingleDate(int n) {
        if (listDate[n] != null) {
            return listDate[n];
        } else return 0l;

    }

    public String getSingleSnow(int n) {
        if (listSnow[n] != null) {
            return listSnow[n];
        } else return "zero";
    }


    public void defineWords(String[] listOfEntries) {
        int i;
        listOfEntries = this.listOfEntries;
        for (i = 0; i < 49; i++) {
            int startindex;
            int endIndex;
            String cutString;
            if (listOfEntries[i] != null && listOfEntries[i] != "") {
                startindex = listOfEntries[i].indexOf("\"temp\":");
                if (startindex != -1) {
                    cutString = listOfEntries[i].substring(startindex);
                    endIndex = cutString.indexOf(",");
                    listTemp[i] = cutString.substring(8, endIndex);
                    //                   listTemp[i] = Double.parseDouble(cutString.substring(8, endIndex));
                }

                startindex = listOfEntries[i].indexOf("\"clouds\":{\"all\":");
                if (startindex != -1) {
                    cutString = listOfEntries[i].substring(startindex);
                    endIndex = cutString.indexOf("}");
                    if (endIndex > 17) {
                        listClouds[i] = cutString.substring(17, endIndex);
//                        listClouds[i] = Double.parseDouble(cutString.substring(17, endIndex));
                    }
                }
                startindex = listOfEntries[i].indexOf("\"rain\":{\"3h\":");
                if (startindex != -1) {
                    cutString = listOfEntries[i].substring(startindex);
                    endIndex = cutString.indexOf("}");
                    //         listRain[i] = Double.parseDouble(cutString.substring(14, endIndex));
                }
                startindex = listOfEntries[i].indexOf("\"snow\":{\"3h\":");
                if (startindex != -1) {
                    cutString = listOfEntries[i].substring(startindex);
                    endIndex = cutString.indexOf("}");
                    if (endIndex > 14) {
                        //             listSnow[i] = Double.parseDouble(cutString.substring(14, endIndex));
                    }
                    startindex = listOfEntries[i].indexOf("\"dt\":");
                    if (startindex != -1) {
                        cutString = listOfEntries[i].substring(startindex);
                        endIndex = cutString.indexOf(",");
                        if (endIndex > 6) {
                            listDate[i] = Long.parseLong(cutString.substring(6, endIndex));
                        }
                    }
                }
            }
        }
    }

    public static String getTemperature(String input) {
        int index = input.indexOf("\"temp\":");
        if (index == -1) {
            return "no data";
        }
        String cutString = input.substring(index);
        int newIndex = cutString.indexOf(",");
        return cutString.substring(7, newIndex);
    }

    public static String getClouds(String input) {
        int index = input.indexOf("\"all\":");
        if (index == -1) {
            return "no data";
        }
        String cutString = input.substring(index);
        int newIndex = cutString.indexOf("}");
        return cutString.substring(6, newIndex);
    }

    public static String getIcon(String input) {
        int index = input.indexOf("\"icon\":");
        if (index == -1) {
            return "no data";
        }
        String cutString = input.substring(index);
        int newIndex = cutString.indexOf("}");
        return cutString.substring(8, newIndex - 1);
    }


}
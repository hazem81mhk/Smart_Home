package server.model.MainServer;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;

public class Schema extends Thread {
    private String start;
    private String end;

    public Schema(String start, String end) {
        this.start = start;
        this.end = end;
        start();
    }

    public boolean checkDate(String start, String end) throws ParseException {
        boolean result = false;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(Calendar.getInstance().getTime());//readLine();
        Date startDate = sdf.parse(start);
        Date endDate = sdf.parse(end);
        Date todaysDate = sdf.parse(currentDate);

        String currentTime = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        LocalTime current_Time = LocalTime.parse(currentTime);
        LocalTime startTime = LocalTime.parse(getTime(start));
        LocalTime endTime = LocalTime.parse(getTime(end));

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        if (todaysDate.after(startDate) || todaysDate.equals(startDate)) {
            if (todaysDate.before(endDate) || todaysDate.equals(endDate)) {
                System.out.println(sdf.format(todaysDate) + " is actually between  " + sdf.format(startDate) + " and " + sdf.format(endDate));
                if (current_Time.isAfter(startTime) && startTime.isBefore(LocalTime.parse("23:59:59"))) {
                    if (endTime.isAfter(LocalTime.parse("00:00:00")) && current_Time.isBefore(endTime)) {
                        System.out.println("The time is right");
                        result = true;
                    }
                }
            }
        } else {
            System.out.println("NO YOUR DATE IS WRONG");
            result = false;
        }
        return result;
    }

    public String getTime(String str) {
        String submitedTime = str.substring(11, str.length());
        return submitedTime;
    }

    public void run() {
        while (true) {
            while (true) {
                try {
                    if (!checkDate(start, end)) break;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                System.out.println("TIME IS PERFECT");
                try {
                    sleep(10000);
                } catch (InterruptedException e) {
                    System.out.println("Sleep problem");
                }
            }
            System.out.println("SORRY very SORRY");
            try {
                sleep(10000);
            } catch (InterruptedException e) {
                System.out.println("SleepProblem");
            }
        }
    }

    public static void main(String[] args) {
        String date1 = "2020-04-18 01:16:00";
        String date2 = "2020-04-18 01:17:00";
        Schema t = new Schema(date1, date2);
    }
}

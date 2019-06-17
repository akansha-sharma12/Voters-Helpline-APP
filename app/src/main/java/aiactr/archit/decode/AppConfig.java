    package aiactr.archit.decode;

    public class AppConfig {
        // Server user login url
        public static String URL_LOGIN = "http://150.242.64.201/android_login_api/login.php";

        public static String URL_EPIC = "http://150.242.64.201/android_login_api/epic_details/epic_detail.php";
        // Server user register url
        public static String URL_REGISTER = "http://150.242.64.201/android_login_api/register.php";


//        Facilities Table
        // Server address for pwd facilities
        public static String URL_FACILITIES = "http://150.242.64.201/android_login_api/facilities/facilities.php";

//        Candidates Table
        // Server address for candidates list
        public static String URL_CANDIDATES = "http://150.242.64.201/android_login_api/candidate/candidates.php";

//        User Table
        // Server address for user login
        public static String URL_USER = "http://150.242.64.201/android_login_api/user_details/user_details.php";

//        Polling Station Table
        // Server address to get list of all polling stations
        public static String URL_GETLISTOFPS = "http://150.242.64.201/android_login_api/polling_station/polling_station_all.php";
        // Server address to get details of a polling station
        public static String URL_GETPS = "http://150.242.64.201/android_login_api/polling_station/polling_station.php";
        // Server address to update queue
        public static String URL_QUEUE_UPDATE = "http://150.242.64.201/android_login_api/polling_station/polling_station_update.php";
    }


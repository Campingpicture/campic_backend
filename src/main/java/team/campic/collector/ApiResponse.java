package team.campic.collector;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse {
    private Response response;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Response {
        private Header header;
        private Body body;

        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Header {
            private String resultCode;
            private String resultMsg;
        }

        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Body {
            private Items items;
            private String numOfRows;
            private String pageNo;
            private String totalCount;

            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Items {
                private java.util.List<Item> item;
            }
        }
    }
}

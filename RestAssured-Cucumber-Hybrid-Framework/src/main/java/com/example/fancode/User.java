package com.example.fancode;

public class User {
    private int id;
    private String name;
    private Address address;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public static class Address {
        private Geo geo;

        public Geo getGeo() {
            return geo;
        }
    }

    public static class Geo {
        private String lat;
        private String lng;

        public double getLat() {
            return Double.parseDouble(lat);
        }

        public double getLng() {
            return Double.parseDouble(lng);
        }
    }
}

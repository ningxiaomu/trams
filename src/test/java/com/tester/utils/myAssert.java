package com.tester.utils;

public class myAssert {
    public void assertIn(String result,String expected){
        try {
            if (result.indexOf(expected) != -1) {
                assert  true;
            } else {
                assert  false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        }
}

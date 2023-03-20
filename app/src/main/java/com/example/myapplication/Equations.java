package com.example.myapplication;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Equations {
    LinkedList<String> list = new LinkedList<>();
    boolean flag = true;
    public Equations() {

        int a;
        int b;
        int c;
        int d=0;
        for(int i=0;i<=9;i++){
            a=i;
            for(int j =0;j<=9;j++){
                b=j;
                for(int k = 0;k<=9;k++){
                    c=k;
                    d=a+b+c;
                    if(a+b+c==d&&d<=9&&d>=0){sendequation(a,'+',b,'+',c,'=',d);}
                    d=a+b-c;
                    if(a+b-c==d&&d<=9&&d>=0){sendequation(a,'+',b,'-',c,'=',d);}
                    d=a+b*c;
                    if(a+b*c==d&&d<=9&&d>=0){sendequation(a,'+',b,'*',c,'=',d);}

                    if(c!=0&&b%c==0) {
                        d=a+b/c;
                        if(a+b/c==d&&d<=9&&d>=0){sendequation(a,'+',b,'/',c,'=',d);}
                    }
                    d=a-b+c;
                    if(a-b+c==d&&d<=9&&d>=0){sendequation(a,'-',b,'+',c,'=',d);}
                    d=a-b-c;
                    if(a-b-c==d&&d<=9&&d>=0){sendequation(a,'-',b,'-',c,'=',d);}
                    d=a-b*c;
                    if(a-b*c==d&&d<=9&&d>=0){sendequation(a,'-',b,'*',c,'=',d);}

                    if(c!=0&&b%c==0) {
                        d=a-b/c;
                        if(a-b/c==d&&d<=9&&d>=0){sendequation(a,'-',b,'/',c,'=',d);}
                    }
                    d=a*b+c;
                    if(a*b+c==d&&d<=9&&d>=0){sendequation(a,'*',b,'+',c,'=',d);}
                    d=a*b-c;
                    if(a*b-c==d&&d<=9&&d>=0){sendequation(a,'*',b,'-',c,'=',d);}
                    d=a*b*c;
                    if(a*b*c==d&&d<=9&&d>=0){sendequation(a,'*',b,'*',c,'=',d);}
                    if(c!=0&&b%c==0) {
                        d=a*b/c;
                        if(a*b/c==d&&d<=9&&d>=0){sendequation(a,'*',b,'/',c,'=',d);}
                    }
                    if(a!=0&&b!=0&&a%b==0) {
                        d=a/b+c;
                        if(a/b+c==d&&d<=9&&d>=0){sendequation(a,'/',b,'+',c,'=',d);}
                        d=a/b+c;
                        if(a/b-c==d&&d<=9&&d>=0){sendequation(a,'/',b,'-',c,'=',d);}
                        d=a/b*c;
                        if(a/b*c==d&&d<=9&&d>=0){sendequation(a,'/',b,'*',c,'=',d);}
                        if(c!=0) {
                            d=a/b/c;
                            if(a/b/c==d&&d<=9&&d>=0 &&( b%c==0||a%c==0)){sendequation(a,'/',b,'/',c,'=',d);}
                        }
                    }
                }
            }
        }
        for(int i=0;i<=9;i++){
            a=i;
            for(int j =10;j<=99;j++){
                b=j;
                d=a+b;
                if((a+b==d)&&d>=10&&d<=99){sendequation(a,'+',b,'=',d);}
                d=a*b;
                if((a*b==d)&&d>=10&&d<=99){sendequation(a,'*',b,'=',d);}

            }
        }

        for(int i=10;i<=99;i++){
            a=i;
            for(int j=0;j<=9;j++){
                b=j;
                d=a+b;
                if((a+b==d)&&d>=10&&d<=99){sendequation(a,'+',b,'=',d);}
                d=a*b;
                if((a*b==d)&&d>=10&&d<=99){sendequation(a,'*',b,'=',d);}
                d=a-b;
                if((a-b==d)&&d>=10&&d<=99){sendequation(a,'-',b,'=',d);}
                if(a!=0&&b!=0&&a%b==0) {
                    d=a/b;
                    if((a/b==d)&&d>=10&&d<=99){sendequation(a,'/',b,'=',d);}
                }

            }
        }
        for(int i=10;i<=99;i++){
            a=i;
            for(int j =10;j<=99;j++){
                b=j;
                d=a-b;
                if((a-b==d)&&d<10&&d>=0){sendequation(a,'-',b,'=',d);}
                if(a!=0&&b!=0&&a%b==0) {
                    d=a/b;
                    if((a/b==d)&&d<10&&d>=0){sendequation(a,'/',b,'=',d);}}

            }
        }
    }

    public void sendequation(int a, char q, int b, char w, int d) {
        StringBuilder sb = new StringBuilder();
        sb.append(a);
        sb.append(q);
        sb.append(b);
        sb.append(w);
        sb.append(d);
        addtoList(sb.toString());
    }

    public void sendequation(int a, char q, int b, char w, int c, char e, int d) {
        StringBuilder sb = new StringBuilder();
        sb.append(a);
        sb.append(q);
        sb.append(b);
        sb.append(w);
        sb.append(c);
        sb.append(e);
        sb.append(d);
        addtoList(sb.toString());
    }
    public void addtoList(String s){
        list.add(s);
    }
    public String returnEquation(){
        Random rand = new Random();
        int random;
        random = rand.nextInt(list.size());
        return list.get(random);
    }
    }


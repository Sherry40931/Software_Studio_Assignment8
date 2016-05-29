1. Basic Info
===============
# name       : assignment8
# author     : 102070028
# file       : Readme
# date       : 05/29/2016

2. About the work
===================
 (1) 改善IP輸入方式（會自動跳格、彈出數字鍵盤）
 (2) 加大calculator按鈕
 (3) server增加scroll panel

3. How to Execute
==================
 (1) Launch server.java in lib/java/com.example on PC
 (2) Launch MainActivity in java/com.csclab.hc.androidpratice on Android device

4. Problem encountered
========================
因為final project就是在做Android和PC的socket連線，所以socket programming基本上沒有遇到什麼困難，
花比較多時間的部分是在改善connect_page.xml，為了讓輸入IP更加方便，除了調整成僅會彈出數字鍵外，
還用TextWatcher新增了輸入格會自動focus下一格的功能，就不用在鍵盤與輸入格之間來回點擊，
因為對於TextWatcher並不熟悉，上網參考許多資料才了解用法。
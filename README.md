# RxJava-android

This project is intented for every Android developer who is interested in Rx extension for Java language.

Covered fields:
 * how to combine Rx extension and Android UI - Button/EditText/Switch etc.
 * how to combine Rx extension and RetroFit + **caching of requests/data during the on configuration change in custom cache OR in RxBus in ReplaySubject**
 * how to combine Rx extension and Firebase
 * how to write your own Observables
 * how to write your own Otto/EventBus like Bus in Rx
 * how to do more complex operations with map functions - filters/maps etc.
 * usage of method references
 * **every sample is written in both classic RxAndroid and in modern lambda manner**


Usage
=====

Since we use [Gradle Retrolambda Plugin](https://github.com/evant/gradle-retrolambda) you have to have [jdk8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) installed on your computer. If you are having any issues, go through [Gradle Retrolambda Plugin](https://github.com/evant/gradle-retrolambda) page for troubleshooting.

Other than that, you should be ready to go. Just clone the repo and have fun!


Used Libraries
==============
 * [RxJava](https://github.com/ReactiveX/RxJava) - Reactive Extensions for the JVM
 * [RxAndroid](https://github.com/ReactiveX/RxAndroid) - RxJava bindings for Android
 * [RxBinding](https://github.com/JakeWharton/RxBinding) - RxJava binding APIs for Android's UI widgets.
 * [Butter Knife](https://github.com/JakeWharton/butterknife) - View "injection" library for Android
 * [Picasso](https://github.com/square/picasso) - A powerful image downloading and caching library for Android
 * [Retrofit](https://github.com/square/retrofit) - Type-safe REST client for Android and Java by Square, Inc.
 * [Firebase](http://www.firebase.com) - Powerful backend services for your app
 * [LeakCanary](https://github.com/square/leakcanary) - A memory leak detection library for Android and Java.


TODOs
=====
 * Retrofit - Since there is new 2.0 beta, I would like to migrate from 1.9.
 * Blog post - link my blogpost to/from this repo
 * Event video record - link video record
 * [RxLifecycle](https://github.com/trello/RxLifecycle) - use this great lib or do something similar on my own
 * Add custom Observable example
 * Add complex example how to use advanced RxJava functions


Known Issues
============
 * [SpellCheckerSession leak](https://code.google.com/p/android/issues/detail?id=172542) - I thought that this is some kind of leak in RxBinding but it seems like problem with SDK


Contribution
============

I will be more than happy if you want to contribute/fork this repo so we can provide more knowledge to our other colleagues.

Since there is no comprehensive tutorial on RxJava/RxAndroid/RetroLambda (in one place), at least what I am aware of, it would be great to include as much as possible code examples. Please, have on mind that I would like to always have code examples for both RxAndroid and lambda way.


Credit
======

This project was created as a part of Developer meetup event held by my current employer [STRV](http://www.strv.com). We are really likeable guys so do not hesitate to check us out! :]


Resources
=========

I would not be able to do this project without the help of few great websites/blog posts. I am so sorry that not everyone will be in this list but I read so many articles that I am unable to remember them all.

 * [ReactiveX](http://reactivex.io/) - home page of whole Rx project. I recommend to read introduction, docs and then feel free to dive into resources for your desired language
 * [Dan Lew's posts](http://blog.danlew.net/2014/09/15/grokking-rxjava-part-1/) - great posts about RxJava, Android and lambda
 * [Roberto Orgiu's posts](https://medium.com/crunching-rxandroid) - another great intro into RxAndroid
 * [RxJava wiki](https://github.com/ReactiveX/RxJava/wiki) - wiki with few examples
 * [Andre Staltz's article](https://gist.github.com/staltz/868e7e9bc2a7b8c1f754) - The introduction to Reactive Programming you've been missing - not Java oriented, but worth reading!


The MIT License (MIT)
=====================

Copyright © 2015 Adam Cerny

Permission is hereby granted, free of charge, to any person
obtaining a copy of this software and associated documentation
files (the “Software”), to deal in the Software without
restriction, including without limitation the rights to use,
copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the
Software is furnished to do so, subject to the following
conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.

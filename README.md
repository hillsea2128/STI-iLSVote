#The official STI Android Application for iLS Voting Systems (STI College Novaliches; Creators: @hillsea2128, @KimPalao)

###LOG Changes: 
1. [02/06/'18, 8:03PM] [APP] [SQL, Node.JS]
--*Fully Working search function (filters out the searched item on the top)
--*Handler timeout now properly resets
--*Abort operation of ```HTTPPost``` recognized by ```onPostExecute()``` AsyncTask class
--*fixed layout bugs
--*fixed memory consumption bugs (Top alotted memory - 125MB, Average memory and processing power use (100 Items loaded with 450kb/s speed) = 86MB, 9% CPU (Tested on 1.3Ghz Quadcore, 1GB Ram phone)
--*fixed network hagging bug
--*added voting state (if the admin turned on the voting system)
[BUGS]: *searching for more
2. [01/23/'18, 8:16PM] [APP] [SQL, Node.JS]
⋅⋅*BUG fixed: ```HTTPPost```/```HTTPGet``` queries and JSON parsing loading times significantly reduced by adding this code each time a ```HTTPClient``` was called:
```
httpGet.abort();
httpPost.abort();
```
⋅⋅*Improved layout smoothness and stability
⋅⋅*Added a ```SwipeRefreshLayout``` to the ```Tab1Fragment``` to refresh the contestant list (Called through custom ```PagerAdapter```
⋅⋅*Fixed verification of access code variable (Including in ```Node.js``` queries)
⋅⋅*Fixed AsnycTask issues and crashes during execution
⋅⋅*Added the user voted contestant lists through new handler ```Contestants_Votes.java``` and with the same adapter. Loaded on tabSelect 
⋅⋅*Fixed bug on wrong integer set with displaying a contestant, thus entering into a mismatch in voted contestant

[BUGS]: Not working search function, handler timeout not resetting after async task execute, ignoring httpget state in executing abort method in ```onPostExecute()``` *searching for more*

3. [01/21/'18, 9:20PM] [APP]

⋅⋅* Added Voting system (querying through HTTPPost)
⋅⋅* Improved Layout bugs (Switched to ViewPager fragment controls, search functionality in action bar)
⋅⋅* Added two fragments on the ViewPager (For contestant lists and for user voted contestants list)
⋅⋅* Working AsyncTask HTTPGet and JSON parsing of Contestants list. (Bug-less)
⋅⋅* Moved Base64 decoding to a AsyncTask and cached for smoother ListView scroll.

[BUGS:] not working search function, Crashing when in EDGE internet connectivity (slow internet, maybe a longer timeout... hihe)

4. [01/21/'18, 9:20PM] [SERVER] -Added Voting system in Microsoft Azure management -Created bugless php queries (Both get and post for voting and contestant lists) *NOTE: Dummy data is present for testing purposes Creator: KimPalao

5. [01/18/'18, 4:32PM] -Added Contestant Lists (AsyncTask HTTPGet and JSON Parsing) *BUG: AsyncTask not responding after a long time, displaying ProgressDialog all the time *MORE BUGS: Error when loading in Android KitKat, Lollipop, and Marshmallow (due to Material incompability stuff)

6. [Hello World] -Added basic layout -Added basic JSON Parsing -Constructed SQL and PHP server-side. (Thanks to the creator: @KimPalao)

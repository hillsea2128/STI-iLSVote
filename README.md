# STI-iLSVote
The official STI Android Application for iLS Voting Systems (STI College Novaliches; Creators: @hillsea2128, @KimPalao)
LOG Changes: 
[01/21/'17, 9:20PM] [APP]    
[1]Added Voting system (querying through HTTPPost)

[2]Improved Layout bugs (Switched to ViewPager fragment controls, search functionality in action bar)

[3]Added two fragments on the ViewPager (For contestant lists and for user voted contestants list)

[4]Working AsyncTask HTTPGet and JSON parsing of Contestants list. (Bug-less)

[5]Moved Base64 decoding to a AsyncTask and cached for smoother ListView scroll.    

[BUGS:] not working search function, Crashing when in EDGE internet connectivity (slow internet, maybe a longer timeout... hihe)

[01/21/'17, 9:20PM] [SERVER] -Added Voting system in Microsoft Azure management -Created bugless php queries (Both get and post for voting and contestant lists) *NOTE: Dummy data is present for testing purposes Creator: KimPalao

[01/18/'17, 4:32PM] -Added Contestant Lists (AsyncTask HTTPGet and JSON Parsing) *BUG: AsyncTask not responding after a long time, displaying ProgressDialog all the time *MORE BUGS: Error when loading in Android KitKat, Lollipop, and Marshmallow (due to Material incompability stuff)

[Hello World] -Added basic layout -Added basic JSON Parsing -Constructed SQL and PHP server-side. (Thanks to the creator: @KimPalao)

Twitter_feed
============
GOAL: To find the top trending topics in a twitter feed.

Steps to compile and execute program:
 1. If eclipse is installed -
 Import this project into eclipse, run the program and see the output in console.
 2. If eclipse is not installed -
 Copy feed.txt and Trend.java into same folder and run following commands.
  - javac Trend.java
  - java Trend
  See the output in console.

Details:
  Twitter feed can be downloaded in various ways using twitter apis. see twitter api documentation.
  But for this project, twitter feed downloaded by an individual is used.
  The tweets from the .csv file from downloaded archive is extracted and copied to Feed.txt file.
  Created a java  Treemap.
  scanned whole file for strings with # as their first character.
  Added that word in treemap. If frequency is null, it is set to zero or increased it by 1.
  Sorted treemap based on frequencies and stored in list.
  Display the sorted list and words.
  
Limitation:
 It is not real time. Dummy static project.  
  
REFERENCES:
  Required java pieces of code is copied from open source projects like stackoverflow or blogs
  Parsing all files in a directory : http://stackoverflow.com/questions/2534632/list-all-files-from-a-directory-recursively-with-java

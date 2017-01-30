### Version
This is the **Time-Sensitive Version** of the checker. Instead of just determining if the bug was encircled or not in their final answer, it also tells how much time it has passed between them seeing that specific slide and them marking the bug for the first time.

Note that this program does not recognize a marking as correct if the bug was encircled earlier but is erased later so that it is not part of the final markings.

### Directory Tree
    root
     + configs
     |  | C#.config
     |  | Cpp.config
     |  | Java.config
     |  | Config Format Explanation.txt
     |
     + source
     |  | BugBox.java
     |  | Checker.java
     |  | DataModel.java
     |  | Mark.java
     |  | MarkConfig.java
     |  | Slide.java
     |  | TimedDataModel.java
     |  | TimedSlide.java 
     |  | Vector.java
     |
     | .gitignore
     | Checker.jar
     | Checker.txt

### How to Use
1. Rename **Checker.txt** to **Checker.bat**. The BAT file was saved as a TXT file to prevent issues when downloading the program
2. Run **Checker.bat**.
3. Supply the proper inputs to the program:
    ```
    Config File Path (no spaces):       <config file of the appropriate PL>
    Input Directory Path (no spaces):   <directory containing all input CSVs>
    Output Directory Path (no spaces):  <directory for output CSVs>
    ```

### Notes
* **IMPORTANT:** The software assumes that the CSV log files are gathered using the 1366 x 768 resolution option
* Since an error on the _Problem 5_ for the C# slideset is too long to fit in the elliptical markings, this program considered it three separate bugs. This means that the program will consider that slide to have **five bugs** instead of three, with the last three being parts of the same line of code.
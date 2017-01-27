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
     |  | MarkConfig.java
     |  | Slide.java
     |  | Vector.java
     |
     + .gitignore
     + Checker.jar
     + Checker.txt

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
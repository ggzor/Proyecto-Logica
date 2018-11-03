#!/bin/python3

def normalize(contents):
  def normalize_line(line):
    spaces = 0
    for c in line:
      if c == ' ':
        spaces += 1
      else:
        break
    
    return (spaces // 2 + 1) * '\t' + line

  return '\n'.join(map(normalize_line, contents.split('\n')))

search = '	<classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-1.8/"/>'
to_add = normalize(\
"""
<classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-1.8/">
  <attributes>
    <attribute name="maven.pomderived" value="true"/>
  </attributes>       
  <accessrules>
    <accessrule kind="accessible" pattern="javafx/**"/>
    <accessrule kind="accessible" pattern="com/sun/javafx/**"/>         
  </accessrules>
</classpathentry>
"""[1:])[:-1] # Remove first line feed and last tabulation

path = './main/.classpath'
destpath = './main/.classpath'

lines = None
with open(path) as source:
  lines = source.readlines()

with open(destpath, 'w') as destination:
  for l in lines:
    if search in l:
      destination.write(to_add)
    else:
      destination.write(l)

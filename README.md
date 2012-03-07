A project for Addepar's coding challenge. 

# MappingAnt.java

This ant relys on a map of the world that is constructed by exploring. 

The ant has two modes, it starts out life as a scouter ant, and for the first 
20 turns, it explores the world. After this it becomes a gatherer, and will 
begin collecting food based on where the closest food that it knows about is. 

When two ants are on the same square, they also talk to each other. They 
update timesteps so that the timestep between the two of them is always the 
same, and then they share their maps, so if one ant has explored an area the 
other hasn't, then the other will have the information about the world that 
the other ant had.
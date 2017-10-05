# Optimize Database System --- Implementing a Better Buffer Manager for SimpleDB Database System

## About SimpleDB System
This semester we will work with the SimpleDB database system - a multi-user transactional
database server written in Java, which interacts with Java client programs via JDBC. SimpleDB is
a stripped-down open-source system developed by E. Sciore for pedagogical use. We have
chosen this as foundation for the CS4432 projects to give you a chance to gain experience with
database systems internals – while attempting to avoid the inherent complexity (aka steep
learning curve) that would come with any industrial-strength system. Instead, the code for
SimpleDB is clean and compact. Thus, the learning curve is small. Everything about SimpleDB
is intentionally bare bone. It implements only a small fraction of SQL and JDBC, and does little
or no error checking.

## Finished Tasks
### Efficient finding of Empty Frame
Design an efficient technique that enables the Buffer Manager to find an empty frame fast (in constant time).  
### Efficient Search for a Given Disk Block
Design an efficient technique that enables the Buffer Manager to figure out whether or not a given disk page, say D, exist in the buffer pool. If exist, then this optimized system is able to return the frame# that contain D efficiently.  
### Efficient Replacement Policy 
Implement buffer replacement policies (LRU & Clock policies) to be used when the buffer is full and choose a frame to evict from the pool. In this optimized database system, it is easy to switch between which policy is used, (we use a configuration parameter that specifies which policy is active (used)).  
### Other Basic Functionalities
The Buffer Manager has to also maintain metadata information indicating whether or not a given frame is pinned (implement that as a pin counter not a Boolean flag). Another information is whether or not a given allocated frame is dirty. An evicted memory page from the buffer pool should be written back to disk if (and only if) it is dirty (has been modified). 
### Reporting Functions
Implement or extend the toString methods for Buffer, BufferMgr, and BasicBufferMgr. The purpose of these methods is to display the contents of the buffer for testing purpose.    
### Testing

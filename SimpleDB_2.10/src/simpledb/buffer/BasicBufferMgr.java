package simpledb.buffer;
import replacementPolicy.LRUPolicy;
import replacementPolicy.ClockPolicy;

import java.util.Hashtable;

import replacementPolicy.ReplacementPolicy;
import simpledb.file.*;

import java.util.*; // CS4432-Project1: In order to use Stack

/**
 * Manages the pinning and unpinning of buffers to blocks.
 * @author Edward Sciore
 *
 */
class BasicBufferMgr {
   private Buffer[] bufferpool;
   private int numAvailable;
   // CS4432-Project1: A stack to store id of free buffers.
   private Stack<Integer> freelist; 
   //CS4432_Project1: A hash table to store the Block and index 
   private Hashtable<Integer, Integer> bufferPagesinPool; 
   //CS4432_Project1: A replacement policy to choose when buffer pool is full.
   private ReplacementPolicy replacementPolicy;

   /**
    * Creates a buffer manager having the specified number 
    * of buffer slots.
    * This constructor depends on both the {@link FileMgr} and
    * {@link simpledb.log.LogMgr LogMgr} objects 
    * that it gets from the class
    * {@link simpledb.server.SimpleDB}.
    * Those objects are created during system initialization.
    * Thus this constructor cannot be called until 
    * {@link simpledb.server.SimpleDB#initFileAndLogMgr(String)} or
    * is called first.
    * @param numbuffs the number of buffer slots to allocate
    */
   BasicBufferMgr(int numbuffs,String replacePolicyMode) {
      bufferpool = new Buffer[numbuffs];
      numAvailable = numbuffs;
      
      if(replacePolicyMode.equals("LRU")){
    	  this.replacementPolicy = new LRUPolicy();	  
      }else if(replacePolicyMode.equals("Clock")){
    	  this.replacementPolicy = new ClockPolicy();
      }
      
      //CS4432-Project1: Allocate  a new hash Table
      bufferPagesinPool = new Hashtable<Integer, Integer>();
      freelist = new Stack<Integer>(); // CS4432-Project1: Allocate a new stack.
      for (int i=0; i<numbuffs; i++) {
    	  bufferpool[i] = new Buffer(i);
       	  freelist.push(numbuffs -1 - i); // CS4432-Project1: Put all the indexes into the freelist.
      }
   }
   
   /**
    * Flushes the dirty buffers modified by the specified transaction.
    * @param txnum the transaction's id number
    */
   synchronized void flushAll(int txnum) {
      for (Buffer buff : bufferpool)
         if (buff.isModifiedBy(txnum))
         buff.flush();
   }
   
   /**
    * Pins a buffer to the specified block. 
    * If there is already a buffer assigned to that block
    * then that buffer is used;  
    * otherwise, an unpinned buffer from the pool is chosen.
    * Returns a null value if there are no available buffers.
    * @param blk a reference to a disk block
    * @return the pinned buffer
    */
   synchronized Buffer pin(Block blk) {
      Buffer buff = findExistingBuffer(blk);
      if (buff == null) {
         buff = chooseUnpinnedBuffer();
         if (buff == null)
            return null;
         buff.assignToBlock(blk);
         //CS4432-Project1: add an entry into the hash table : block hash code -> buffer ID
//         String blkStr = blk.toString();
         bufferPagesinPool.put(blk.hashCode(), buff.getBufID());
      }
      if (!buff.isPinned()){
         numAvailable--;
         int bufID = buff.getBufID(); // CS4432-Project1: Get the ID of the existing buffer.
       	 int position = freelist.search(bufID); // CS4432-Project1: Find the position of that index in the free list.
       	if (position > 0){
       	 int[] temp = new int[position -1]; // CS4432-Project1: Allocate an array to store indexes.
       	 // CS4432-Project1: Put all the indexes on the needed index into the array.
       	 for (int i = 0; i < position - 1; i++){
       		 temp[i] = freelist.pop();
       	 }
       	 // CS4432-Project1: Remove the corresponding ID in the free list.
      		 freelist.pop();
      		 // CS4432-Project1:  push the rest of buffer id back.
      		 for (int i = 0; i < position - 1; i++){
      			 freelist.push(temp[position - 1 - i]);
      		 }
      	 }
      }
      buff.pin();
    //CS4432_Project1: set reference bit as 1
      buff.setReferenceBit();
      return buff;
   }


/**
    * Allocates a new block in the specified file, and
    * pins a buffer to it. 
    * Returns null (without allocating the block) if 
    * there are no available buffers.
    * @param filename the name of the file
    * @param fmtr a pageformatter object, used to format the new block
    * @return the pinned buffer
    */
   synchronized Buffer pinNew(String filename, PageFormatter fmtr) {
      Buffer buff = chooseUnpinnedBuffer();
      if (buff == null)
         return null;
      buff.assignToNew(filename, fmtr);
      //CS4432-project1:
      bufferPagesinPool.put(buff.block().hashCode(), buff.getBufID());
      numAvailable--;
      buff.pin();
      //CS4432_Project1: set reference bit as 1
      buff.setReferenceBit();
      return buff;
   }
   
   /**
    * Unpins the specified buffer.
    * @param buff the buffer to be unpinned
    */
   synchronized void unpin(Buffer buff) {
	   //CS4432_project1if the specified buffer unpinned in the buffer pool, 
	   //we need to remove the block which in that specified buffer from hashTable
//	  if (buff.block() != null) {
//		  bufferPagesinPool.remove(buff.block().hashCode());	
//	  }
	   
      buff.unpin();
      if (!buff.isPinned()) {
          numAvailable++;
//          int BufferID = buff.getBufID(); // CS4432-Project1: 
//          freelist.push(BufferID); // CS4432-Project1: 
      }
   }
   
   /**
    * Returns the number of available (i.e. unpinned) buffers.
    * @return the number of available buffers
    */
   int available() {
      return numAvailable;
   }
   
   private Buffer findExistingBuffer(Block blk) {
	   //CS4432_project1:Find the given block's index,if it is  exist 
	   // return the buffer with that index else return null.
//	   String blkStr = blk.toString();
	   int hashCode = blk.hashCode();
	   Integer index = bufferPagesinPool.get(hashCode);
	   if (index != null){
		   return bufferpool[index];
	   } else{
		   return null;
	   }
//      for (Buffer buff : bufferpool) {
//         Block b = buff.block();
//         if (b != null && b.equals(blk))
//            return buff;
//      }
//      return null;
   }
   /* CS4432-project1: The purpose of this method to get an available buffer
    * First, it tried to look for an empty frame by looking at the the freelist
    * if there are no buffer, we try to choose our replacement policy(LRU or clock) to get a frame
    * no frame could be available, it will return null
    */
   
   private Buffer chooseUnpinnedBuffer() {
	  // CS4432-Project1: check if there exist empty buffers
	  if (!freelist.isEmpty()) {
		  int BufferID = freelist.pop();
		  return bufferpool[BufferID];
	  }else{
		  if(numAvailable == 0){
			  return null;	  
		  }else{
			  int BufferID = replacementPolicy.chooseBufferForReplacement(bufferpool);	
			  Buffer buf = this.bufferpool[BufferID];
			  if (buf.block() != null) {
				  bufferPagesinPool.remove(buf.block().hashCode());	
			  }
			  return buf;
		  }
		  
	  }
   }
   
   //CS4432_Project1: return the free list 
   public Stack<Integer> getFreelist() {
	return freelist;
}
   //CS4432_Project1: return the hash table of block and buffer page ID
   public Hashtable<Integer, Integer> getBufferPagesinPool() {
		return bufferPagesinPool;
	}
   //CS4432-Project1:
   public String toString() {
	   String str = "";
	 for(int i = 0; i < bufferpool.length; i++){
		 str += bufferpool[i].toString();
		 str += "\n";
	 }
	return str;
   }
   
}

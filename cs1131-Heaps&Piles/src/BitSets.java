
/**
 * FOR HUFFMAN ENCODING OUTPUT
 * 
 * @author Brett Kriz
 */
public class BitSets {
    /*
        
            BITS
    -----------------------------
        int 32 bit, 4 bits
        0b <- binary flag
    0b0101
     \/ -- 29 zeros
    0..0101
    
        0-9 A-F // 16 possible vals hex
    0x5 // equal to above binaryflag
    
    1101011001 shift left 5
        SO
    Shift everything left 5 binary positions
    110101100100000 // If you get to the end of the 32 bits, you loose value
    ^ 
            Shift right 3 of above then
    110101100100
    
    
    For ints...
    [ |   31 ] 
     ^ sign bit
    There are 2 kinds of right shift
    1 110------
    1 111110--
    
    << left shit
    >> right shit
    >>> right shift ignore sign bit
    --------------
    0x1=0b1=1
    0x1 << S // Set s bit to be 1
    0x1 << 0 = 0 x1
    0x1 << 1 = 0b10 = 0x2 = 2
    0x1 << 2 = 0b100= 0x4 = 4
    0x1 << 5 = 0x10 = 32
    
    All Zero int
    00...0
    All zeros excpt end 1
    00...01 << 5
    
        100000
    &   000000
    ------------
        000000
    
        100000
    |   000000
    ------------
        100000
    
    By and we enable the 6th  it to be true
    
    [ Jason, Sally, Beth, Joe ]
        0       1     2     3
    
        0000
    |   0001
    ---------
        0001
    |   0100    (0x1 << 2)
    ---------
        0101
    
    // Effiecient when limited number of bytes and making many copies
    // Bitwise ops are as fast as arithmatic operatioins
    
    
    ~~~ BITSET ~~~
      s <- add(S, i)            return S | (0x1 << i);
      s <- remove(S, i)         return S & ~(0x1 << i);     // ~ is the nice form of not
      book <- contains(S, i)    return 0 != (S & (0x1 <<  i);
      s <- toggle(S, i)         return S ^ (0x1 << i);      // Yes xor
      S <- clear()              return 0;
      s <- Universe()           return ~0;  // FLip of zero
      s <- Universe(N)          return (ox1 << (N+1))-1
    
    
    For remove:
        110100 remove 2
        if not at 2 we want it to be the same
        if we are at 2 we want it to nolonger exist
        110100
    &   111011      // BIT MASKS
    -----------
        110000
    
    But how do we generate bitmasks?
        We need to flip everthing, using the op ~
        ~(0x1 << i)
    
    For contains:
        To see if it containts, we create a mask that points to that val
    
        110100
    &   010000
    -----------
        010000
    
    For Universe:
        Every possible entry
        Sometimes want to use a bitset but we dont have a fixed size
    
        Given an array from user
        U = [A,C,B,D,E]
    
        // We want the bits to reflect size
    
        The one acception when we can use an airthmatic operator...
        32 16 8 4 2 1
        000001 = 1
        000011 = 3
        000111 = 7
        001111 = 15
        011111 = 31
        // All (power of 2) - 1
        // 2^(i+1) - 1
        
        Pow is bad, but val can be activated by single bits
    (0x1 << (i+1))-1
        000001 = 1
        000010 = 2
        000100 = 4
        001000 = 8
        010000 = 16
    
        
    
    For Toggle:
        Were gonna make a truth table
        Val     ToToggle    |   Val should be (XOR ^)
        1       1           |   0
        1       0           |   1
        0       1           |   1
        0       0           |   0
    
    ~~~ TO ITERATE OVER A BITSET ~~~
        for(int i = 0; i < 32; i++){
            if (contains(S,i)){
                // Do whatever
            {
        }
    
    ~~~~~~~~~~PACKING~~~~~~~~~~~~~~~
    
        byte a,b,c,d
        |   |   |   |   |
          ^8  ^8  ^8  ^8
    
    PACK:
            int  r = 0;
            r = r | a << 24;
            r = r | b << 16;
            r = r | c << 8;
            r = r | d;
            
        Packing things with uneven length (LIKE HUFFMAN ENCODING)
        Doing it with or
    
    UNPACK:
            a = r >>> 24;
            b = r >>> 16 & 0xFF;
            c = (r >>> 8) & 0xFF;
            d = r & 0xFF
    
            SO we need to mask this
            00...          0|11111 // that mask is F F in hex
            |   |   |   |   |
    
    
    
    
    
    
    
    
    
    */
}

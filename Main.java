import java.util.HashMap;
import java.util.Scanner;

class Main{
    enum WritePolicy{

        WRITE_BACK("wb"),
        WRITE_THROUGH("wt");

        private String value;
        private static HashMap map = new HashMap<>();

        private WritePolicy(String value){
            this.value = value;
        }
        static{
            for (WritePolicy writePolicy : WritePolicy.values()) {
                map.put(writePolicy.value, writePolicy);
            }
        }
        public static WritePolicy valueof(String writePolicy) {
            return (WritePolicy) map.get(writePolicy);
        }
        public String getValue(){
            return value;
        }
    }

    enum AllocatePolicy{

        WRITE_ALLOCATION("wa"),
        NO_WRITE_ALLOCATION("nw");

        private String value;
        private static HashMap map = new HashMap<>();

        private AllocatePolicy(String value){
            this.value = value;
        }
        static{
            for (AllocatePolicy allocatePolicy : AllocatePolicy.values()) {
                map.put(allocatePolicy.value, allocatePolicy);
            }
        }
        public static AllocatePolicy valueof(String allocatePolicy) {
            return (AllocatePolicy) map.get(allocatePolicy);
        }
        public String getValue(){
            return value;
        }
    }

    enum CacheMode{

        UNIFIED(0),
        SEPARATED(1);

        private int value;
        private static HashMap map = new HashMap<>();

        private CacheMode(int value){
            this.value = value;
        }
        static{
            for (CacheMode cacheMode : CacheMode.values()) {
                map.put(cacheMode.value, cacheMode);
            }
        }
        public static CacheMode valueOf(int cacheMode) {
            return (CacheMode) map.get(cacheMode);
        }
        public int getValue(){
            return value;
        }
    }
    

    class Cache{
        private CacheMode cacheMode;
        private int cacheDataSize;
        private int cacheInstructionSize;
        private int blockSize;
        private int associativity;
        private WritePolicy writePolicy;
        private AllocatePolicy allocatePolicy;

        private int instructionsAccesses;
        private int instructionsMisses;
        private int instructionsReplaces;
        
        private int dataAccesses;
        private int dataMisses;
        private int dataReplaces;

        private int allFetch;
        private int allCopies;

        public Cache(int cacheMode, int cacheDataSize, int cacheInstructionSize, int blockSize, int associativity, String writePolicy, String allocatePolicy){
            this.cacheMode = CacheMode.valueOf(cacheMode);
            this.cacheDataSize = cacheDataSize;
            this.cacheInstructionSize = cacheInstructionSize;
            this.blockSize =  blockSize;
            this.associativity = associativity;
            this.writePolicy = WritePolicy.valueof(writePolicy);
            this.allocatePolicy = AllocatePolicy.valueof(allocatePolicy);


            instructionsAccesses = 0;
            instructionsMisses = 0;
            instructionsReplaces = 0;

            dataAccesses = 7;
            dataMisses = 5;
            dataReplaces = 0;

            allFetch = 12;
            allCopies = 2;
        }

        /**
         * @return the cacheMode
         */
        public CacheMode getCacheMode() {
            return cacheMode;
        }

        /**
         * @return the cacheDataSize
         */
        public int getCacheDataSize() {
            return cacheDataSize;
        }

        /**
         * @return the cacheInstructionSize
         */
        public int getCacheInstructionSize() {
            return cacheInstructionSize;
        }

        /**
         * @return the blockSize
         */
        public int getBlockSize() {
            return blockSize;
        }

        /**
         * @return the associativity
         */
        public int getAssociativity() {
            return associativity;
        }

        /**
         * @return the writePolicy
         */
        public WritePolicy getWritePolicy() {
            return writePolicy;
        }

        /**
         * @return the allocatePolicy
         */
        public AllocatePolicy getAllocatePolicy() {
            return allocatePolicy;
        }

        public void readCacheLine(int type, int address){
            
        }

        @Override
        public String toString() {
            String result = "***CACHE SETTINGS***\n";
            if(cacheMode.equals(CacheMode.UNIFIED)){
                result = result + "Unified I- D-cache\n" + "Size: " + cacheDataSize + "\n";
            } else {
                result = result + "Split I- D-cache\n" + "I-cache size: " + cacheInstructionSize + "\n" +
                "D-cache size: " + cacheDataSize + "\n";
            }
            result = result + "Associativity: " + associativity + "\n";
            result = result + "Block size: " + blockSize + "\n";
            if(writePolicy.equals(WritePolicy.WRITE_BACK)){
                result = result + "Write policy: WRITE BACK\n";
            } else {
                result = result + "Write policy: WRITE THROUGH\n";
            }
            if(allocatePolicy.equals(AllocatePolicy.WRITE_ALLOCATION)){
                result = result + "Allocation policy: WRITE ALLOCATE\n\n";
            } else {
                result = result + "Allocation policy: WRITE NO ALLOCATE\n\n";
            }
            result = result + "***CACHE STATISTICS***\n";
            result = result + "INSTRUCTIONS\n";
            result = result + "accesses: " + instructionsAccesses + "\n";
            result = result + "misses: " + instructionsMisses + "\n";
            if(instructionsAccesses != 0){
                result = result + "miss rate: " + String.format("%.4f ", ((double)instructionsMisses)/((double)instructionsAccesses)) + "(hit rate " + String.format("%.4f)", 1 - ((double)instructionsMisses)/((double)instructionsAccesses)) + "\n";
            } else {
                result = result + "miss rate: 0.0000 (hit rate 0.0000)\n";
            }
            result = result + "replace: " + instructionsReplaces + "\n";
            result = result + "DATA\n";
            result = result + "accesses: " + dataAccesses + "\n";
            result = result + "misses: " + dataMisses + "\n";
            result = result + "miss rate: " + String.format("%.4f ", ((double)dataMisses)/((double)dataAccesses)) + "(hit rate " + String.format("%.4f)", 1 - ((double)dataMisses)/((double)dataAccesses)) + "\n";
            result = result + "replace: " + dataReplaces + "\n";
            result = result + "TRAFFIC (in words)\n";
            result = result + "demand fetch: " + allFetch + "\n";
            result = result + "copies back: " + allCopies + "\n";

            return result;
        }
    }

    class Block{
        private boolean isValid;
        private boolean isDirty;
        private int tag;

        public int getTag() {
            return tag;
        }

        public boolean isDirty() {
            return isDirty;
        }

        public boolean isValid() {
            return isValid;
        }

        public void setDirty(boolean isDirty) {
            this.isDirty = isDirty;
        }

        public void setTag(int tag) {
            this.tag = tag;
        }

        public void setValid(boolean isValid) {
            this.isValid = isValid;
        }
    }
    public static void main(String[] args) {
        Main main = new Main();
        Scanner scan = new Scanner(System.in);
        String[] firstLine = scan.nextLine().split("-");
        String[] secondLine = scan.nextLine().split("-");
        Cache testCache;
        
        if(Integer.parseInt(firstLine[1].trim()) == 0){
            testCache = main.new Cache(Integer.parseInt(firstLine[1].trim()), Integer.parseInt(secondLine[0].trim()), 0, Integer.parseInt(firstLine[0].trim()), Integer.parseInt(firstLine[2].trim()), firstLine[3].trim(), firstLine[4].trim());
        } else{
            testCache = main.new Cache(Integer.parseInt(firstLine[1].trim()), Integer.parseInt(secondLine[1].trim()), Integer.parseInt(secondLine[0].trim()), Integer.parseInt(firstLine[0].trim()), Integer.parseInt(firstLine[2].trim()), firstLine[3].trim(), firstLine[4].trim());
        }
        System.out.println(testCache);
    }
}
package hash;

public class MyHashTable {
    private Node[] arr = new Node[11];
    // 2. 维护哈希表中的有的元素个数
    private int size;

    // true: key 之前不在哈希表中
    // false: key 之前已经在哈希表中
    public boolean insert(Integer key) {
        // 1. 把对象转成 int 类型
        // hashCode() 方法的调用是核心
        int hashValue = key.hashCode();
        // 2. 把 hashValue 转成合法的下标
        int index = hashValue % arr.length;
        // 3. 遍历 index 位置处的链表，确定 key 在不在元素中
        Node current = arr[index];
        while (current != null) {
            // equals() 方法的调用是核心
            if (key.equals(current.key)) {
                return false;
            }
            current = current.next;
        }
        // 4. 把 key 装入节点中，并插入到对应的链表中
        // 头插尾插都可以，头插相对简单
        Node node = new Node(key);
        node.next = arr[index];
        arr[index] = node;

        // 5. 维护 元素个数
        size++;
        //6.通过维护负载因子来维护冲突率
        if((double)(size / arr.length) >= 0.75) {
            扩容();
        }

        return true;
    }

    private void 扩容() {//O(n)
        Node[] newArr = new Node[arr.length * 2];
        //不能直接按链表搬运，因为元素保存的下标和数组长度有关，数组长度变了，下标也会变
        //需要把每个人元素重新计算一遍

        //遍历整个数组
        for(int i = 0; i < arr.length ; i++) {
            Node cur = arr[i];
            while(cur != null) {
                Integer key = cur.key;
                Integer hashValue = key.hashCode();
                int index = hashValue % newArr.length;

                //头插
                Node node = new Node(key);
                node.next = newArr[index];
                newArr[index] = node;

                cur = cur.next;
            }
        }
        arr = newArr;
    }

    public boolean remove (Integer key) {
        int hashValue = key.hashCode();
        int index = hashValue % arr.length;
        Node cur = arr[index];
        Node prev = null;
        while(cur != null) {
            if(key.equals(cur.key)) {
                //删除
                if(prev != null) {
                    prev.next = cur.next;
                } else {
                    arr[index] = cur.next;
                }
                size--;
                return true;
            }
            prev = cur;
            cur = cur.next;
        }
        return false;
    }
    public boolean contains(Integer key) {
        int hashValue = key.hashCode();
        int index = hashValue % arr.length;
        Node cur = arr[index];
        while(cur != null) {
            if(key.equals(cur.key)) {
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

}

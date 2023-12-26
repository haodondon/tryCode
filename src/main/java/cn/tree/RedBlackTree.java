package cn.tree;

/**
 * @Description: 红黑树
 * @Author 一枚路过的程序猿
 * @Date 2023/11/14 14:07
 * @Version 1.0
 *

        红黑树规则：
            1.每个节点要么是黑色，要么是红色
            2.根节点必须是黑色
            3.每个叶子节点是黑色（存在两个null黑色节点）
            4.每个红色节点的两个子节点都是黑色
            5.任意一个节点到每个叶子结点的路径都包含相同数量的黑色节点

 */
public class RedBlackTree {

    private Node root;

    public void add(Integer data){

        Node node = new Node(data);

        if (root == null) {

            root = node;

        }else{

            node.red = true;

            Node tempNode = root;

            for(;;){

                /** 小于的话从左边插入 */
                if (data < tempNode.val) {

                    if (tempNode.left == null) {

                        node.parent = tempNode;
                        tempNode.left = node;
                        break;

                    }

                    tempNode = tempNode.left;

                /** 大于的话从右边插入 */
                }else if (data > tempNode.val){

                    if (tempNode.right == null) {

                        node.parent = tempNode;
                        tempNode.right = node;
                        break;

                    }

                    tempNode = tempNode.right;

                /** 等于直接跳出 */
                }else {
                    break;
                }

            }

            balanceInsertion(node);

        }

    }

    private void balanceInsertion(Node node) {

        if (node != null && node != root && node.parent.red) {

            /** 校验当前是否是左子树 */
            if(node.parent == node.parent.parent.left){

                /**
                 *
                 * 校验是否存在叔叔节点，如果存在
                 *      1.父亲节点和叔叔节点变为黑色
                 *      2.爷爷节点变为红色
                 *      3.以爷爷节点进行递归插入（防止爷爷节点之上还有其余节点，导致整棵树不平衡）
                 *
                 *          3
                 *         / \
                 *        1   4
                 *       /
                 *      0
                 *
                 * */
                if(node.parent.parent.right != null && node.parent.parent.right.red){

                    node.parent.red = false;
                    node.parent.parent.right.red = false;
                    node.parent.parent.red = true;

                    balanceInsertion(node.parent.parent);

                }else { /** 不存在叔叔节点 */

                    /**
                     *
                     * 校验节点是否在右侧，如果是，首先以父亲节点进行一次左旋操作，变成普通左子树
                     *
                     *          3               3
                     *         /               /
                     *        1               2
                     *         \             /
                     *          2           1
                     *
                     * */
                    if(node == node.parent.right){

                        /** 左旋 */
                        rotateLeft(node.parent);

                    }

                    /**
                     *
                     * 如果是普通左子树
                     *      1.父亲节点变为黑色
                     *      2.爷爷节点变为红色
                     *      3.然后进行右旋
                     *      4.以爷爷节点进行递归插入（防止爷爷节点之上还有其余节点，导致整棵树不平衡）
                     *
                     *          3               1
                     *         /               / \
                     *        1               0   3
                     *       /
                     *      0
                     *
                     * */

                    /** 变色 */
                    node.parent.red = false;
                    node.parent.parent.red = true;

                    /** 右旋 */
                    rotateRight(node.parent.parent);

                    /** 继续向上递归 */
                    balanceInsertion(node.parent.parent);

                }

            }else { /** 右子树 */

                /**
                 *
                 * 校验是否存在叔叔节点，如果存在
                 *      1.父亲节点和叔叔节点变为黑色
                 *      2.爷爷节点变为红色
                 *      3.以爷爷节点进行递归插入（防止爷爷节点之上还有其余节点，导致整棵树不平衡）
                 *
                 *          3
                 *         / \
                 *        1   4
                 *             \
                 *              0
                 *
                 * */
                if(node.parent.parent.left != null && node.parent.parent.left.red){

                    node.parent.red = false;
                    node.parent.parent.left.red = false;
                    node.parent.parent.red = true;

                    balanceInsertion(node.parent.parent);

                }else { /** 不存在叔叔节点 */

                    /**
                     *
                     * 校验节点是否在左侧，如果是，以父亲节点进行一次右旋操作，变成普通右子树
                     *
                     *          3               3
                     *           \               \
                     *            1               2
                     *           /                 \
                     *          2                   1
                     *
                     * */
                    if(node == node.parent.left){

                        /** 右旋 */
                        rotateRight(node.parent);

                    }

                    /**
                     *
                     * 如果是普通右子树
                     *      1.父亲节点变为黑色
                     *      2.爷爷节点变为红色
                     *      3.然后进行左旋
                     *      4.以爷爷节点进行递归插入（防止爷爷节点之上还有其余节点，导致整棵树不平衡）
                     *
                     *          3                    1
                     *           \                  / \
                     *            1                0   3
                     *             \
                     *              0
                     *
                     * */

                    /** 变色 */
                    node.parent.red = false;
                    node.parent.parent.red = true;

                    /** 右旋 */
                    rotateLeft(node.parent.parent);

                    /** 继续向上递归 */
                    balanceInsertion(node.parent.parent);

                }
            }

        }

        root.red = false;

    }


    /**
     *
     * 左旋:
     * <p>
     *              3                           6
     *            /  \                        /  \
     *           2    6                      3    7
     *              /  \                    / \
     *             5    7                  2   5
     */
    private void rotateLeft(Node node) {

        /** 获取右子节点 -> 6 */
        Node r = node.right;

        /** node右子节点设置为 r节点的左子节点 -> ( 3的右子节点设置为5 )*/
        node.right = r.left;

        /** 校验 r的左子节点 是否为空 */
        if (r.left != null) {

            /** 设置 r的左子节点父类为node -> ( 5的父类设置为3 ) */
            r.left.parent = node;
        }

        /** 将6的父类设置为3的父类 */
        r.parent = node.parent;

        /** 校验node节点是否存在父类 */
        if(node.parent == null){

            /** 根节点直接设置为 r -> ( 根节点直接设置为6 ) */
            root = r;

        }else if (node.parent.left == node) { /** 校验 node 节点是存在父类的哪边 -> 左 */
            node.parent.left = r;
        }else { /** 右 */
            node.parent.right = r;
        }

        /** 交换位置 -> （6和3交换位置） */
        node.parent = r;
        r.left = node;

    }


    /**
     * 右旋:
     * <p>
     *         6                        3
     *       /  \                     /  \
     *      3    7                   2    6
     *     / \                          / \
     *    2   5                        5   7
     *
     */
    private void rotateRight(Node node) {

        /** 获取左子节点 -> （3） */
        Node l = node.left;

        /** 更新node左节点为 l的右节点 -> （ 6的左子节点更新为5 ） */
        node.left = l.right;

        /** 校验 l的右节点 是否为空，不为空的时候进行双向关联  -> （ 5的父类更新为6 ）*/
        if (l.right != null) {
            l.right.parent = node;
        }

        /** 将3的父类设置为6的父类 */
        l.parent = node.parent;

        if (node.parent == null) {

            /** 根节点直接设置为 r -> ( 根节点直接设置为3 ) */
            root = l;

        }else if (node.parent.left == node) {  /** 校验 node 节点是存在父类的哪边 -> 左 */
            node.parent.left = l;

        }else { /** 右 */
            node.parent.right = l;

        }

        /** 交换位置 -> （6和3交换位置） */
        node.parent = l;
        l.right = node;

    }

    /**
     *  删除节点
     *      1.删除叶子节点，直接删除
     *      2.删除节点有一个叶子节点的，那么用叶子节点来代替
     *      3.删除结点有两个叶子节点，需要使用前驱查找或者后继查找来替换
     * @param data
     */
    public void remove(Integer data){

        /** 查找到节点 */
        Node node = getNode(data);

        if (node == null) {
            return;
        }

        /** 第三种情况：删除结点有两个叶子节点，需要使用前驱查找或者后继查找来替换 */
        if (node.left != null && node.right != null){

            /** 采用前驱查找 */
            Node n = precursorFind(node);

            /** 修改值为待删除的值 */
            node.val = n.val;

            /**
             *  把当前要删除的节点修改为后继查找的节点
             *
             *       3
             *     /  \
             *    2   10      -- 11               比如这个时候要删除 10节点，我们通过（后继查找）查找到了 11节点，
             *       / \                          只需要将10修改为11，然后删除 11节点即可
             *      9   11    -- 删除
             *
             */
            node = n;

        }

        /** 找到需要替换的节点 */
        Node n = node.left != null ? node.left : node.right;

        /** 第二种情况：删除节点有一个叶子节点的，那么用叶子节点来代替 */
        if(n != null){

            /** 直接关联，跳过待删除节点 */
            n.parent = node.parent;

            /** 如果删除节点的父节点为空，表示删除之后只会存在一个节点，直接指向root */
            if(node.parent == null){
                root = n;

                /** 如果删除节点在父节点的左边，把替换节点关联到父节点的左边 -> 双向绑定 */
            }else if(node == node.parent.left){
                node.parent.left = n;

                /** 如果删除节点在父节点的右边，把替换节点关联到父节点的右边 -> 双向绑定 */
            }else {
                node.parent.right = n;
            }

            /** help GC */
            node.right = node.left = node.parent = null;

            /** 调整红黑树平衡，只有删除黑色节点时，需要调整平衡，因为其余的都是修改，不会发生树调整 */
            if(!node.red){
                fixTree(n);
            }

            /** 第一种情况：删除叶子节点，直接删除 */
        }else {

            /** 此处需要先进行调整平衡，然后再删除 */
            if(!node.red){
                fixTree(node);
            }

            /** 如果只有一个叶子节点，并且没有父类，删除之后，整棵树为空，置空root节点 */
            if (node.parent == null) {
                root = null;

                /** 如果删除节点在父节点的左边，把替换节点关联到父节点的左边 -> 双向绑定 */
            }else if (node == node.parent.left) {
                node.parent.left = null;

                /** 如果删除节点在父节点的右边，把替换节点关联到父节点的右边 -> 双向绑定 */
            }else {
                node.parent.right = null;
            }

            /** help GC */
            node.parent = null;

        }

    }

    /**
     * 调整树平衡
     * @param node
     */
    private void fixTree(Node node) {

        while (node != root && !node.red){

            /** 校验节点是左节点还是右节点 */
            if(node == node.parent.left){

                /** 获取兄弟节点 */
                Node r = node.parent.right;

                // 校验是不是真的兄弟节点
                if(r.red){

                    r.red = false;
                    node.parent.red = true;
                    rotateLeft(node.parent);
                    r = node.parent.right;

                }

                /** 当兄弟节点一个子节点都没有，或者两个子节点都是黑色 */
                if (r.left == null && r.right == null || ((r.left != null && !r.left.red) && (r.right != null && !r.right.red))) {

                    r.red = true;
                    node = node.parent;

                }else { /** 至少存在一个子节点 */

                    /**
                     * 如果兄弟节点的子节点是左子节点，需要变色 + 右旋
                     * */
                    if(r.right == null){ /** 如果兄弟节点的右子节点为空，左子节点肯定不为空，因为兄弟节点没有子节点的时候，只会进入上的if */

                        r.red = true;
                        r.left.red = false;
                        rotateRight(r);

                        /** 右旋之后，兄弟节点发生变化，需要重新指定 */
                        r = node.parent.right;

                    }

                    /** 然后根据父节点进行左旋 + 变色 */
                    r.red = node.parent.red;
                    node.parent.red = r.right.red = false;
                    rotateLeft(node.parent);

                    node = root; /** 退出循环 */
                }

            }else {

                /** 获取兄弟节点 */
                Node r = node.parent.left;

                // 校验是不是真的兄弟节点
                if(r.red){

                    r.red = false;
                    node.parent.red = true;
                    rotateRight(node.parent);
                    r = node.parent.left;

                }

                /** 当兄弟节点一个子节点都没有，或者两个子节点都是黑色 */
                if (r.left == null && r.right == null || ((r.left != null && !r.left.red) && (r.right != null && !r.right.red))) {

                    r.red = true;
                    node = node.parent;

                }else { /** 至少存在一个子节点 */

                    /**
                     * 如果兄弟节点的子节点是左子节点，需要变色 + 右旋
                     * */
                    if(r.left == null){ /** 如果兄弟节点的右子节点为空，左子节点肯定不为空，因为兄弟节点没有子节点的时候，只会进入上的if */

                        r.red = true;
                        r.right.red = false;
                        rotateLeft(r);

                        /** 右旋之后，兄弟节点发生变化，需要重新指定 */
                        r = node.parent.left;

                    }

                    /** 然后根据父节点进行左旋 + 变色 */
                    r.red = node.parent.red;
                    node.parent.red = r.left.red = false;
                    rotateRight(node.parent);

                    node = root; /** 退出循环 */
                }

            }

        }

        node.red = false;

    }

    public Node getNode(Integer data){

        Node node = root;

        while (node != null && data != null){

            if(data < node.val){
                node = node.left;
            }else if(data > node.val){
                node = node.right;
            }else {
                return node;
            }

        }

        return null;

    }

    /**
     * 前驱节点查询
     *      树右边最大的节点
     * @param node
     * @return
     */
    private Node precursorFind(Node node){

        node = node.left;

        if (node == null) {
            return null;
        }

        while (node.right != null) {

            node = node.right;

        }

        return node;

    }

    /**
     * 后继节点查询
     *      树左边最小的节点
     * @param node
     * @return
     */
    private Node subsequentFind(Node node){

        node = node.right;
        if (node == null) {
            return null;
        }

        while (node.left != null) {

            node = node.left;

        }

        return node;

    }

    public static class Node{

        private boolean red;

        private Integer val;

        private Node parent;

        private Node left;

        private Node right;

        public Node(Integer val) {
            this.val = val;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public boolean isRed() {
            return red;
        }

        public void setRed(boolean red) {
            this.red = red;
        }

        public Integer getVal() {
            return val;
        }

        public void setVal(Integer val) {
            this.val = val;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }
}

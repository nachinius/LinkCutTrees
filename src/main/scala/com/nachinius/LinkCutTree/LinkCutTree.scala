package com.nachinius.LinkCutTree

import com.nachinius.splay.Node

import scala.collection.mutable

class LinkCutTree[A] extends AbstractLinkCutTree[A] {

  val roots: mutable.Set[Vertex] = mutable.Set[Vertex]()


  /**
    * create a new tree in the forest
    *
    * @param elem associated data to the vertex
    */
  override def makeTree(elem: A): Vertex = {
    val vertex = Vertex(new Node(0, elem))
    roots += vertex
    vertex
  }

  override def findRoot(v: Vertex): Vertex = {
    access(v)
    Vertex(v.leftist.splay)
  }

  /**
    * Perform a calculate `f` on the vertices of the path
    * that goes from `v` to `root`
    *
    * @param v
    */
  def pathAggregate[U](v: Vertex, f: A => U): Unit = {
    access(v)
    v.node.foreach(n => f(n.elem))
  }

  /**
    * Helper function that marks root-to-v as the preferred-path
    * make the `v` the root of its aux tree
    *
    * @param v
    */
  private def access(v: Vertex) = {
    // becomes root of its aux-tree, its left are nodes upper-path in the
    // real tree, and its right are nodes down-path in the real tree
    v.splay()
    // since accessing, make v the preferred, and its children don't belong
    // to preferred path any longer, we cut them, to make it part of its own
    // aux tree
    val cutted: Option[Vertex] = v.split
    // but we keep a link (path-parent) to `v`
    cutted.foreach(_.setPathParent(v))

    // now, we join x(v), with all its possible path-parent until becomes the root
    while (v.pathParent.nonEmpty) {
      val w: Vertex = v.pathParent.get
      w.splay()
      // the new preferred child of w, should be v
      // whatever remains at w.right is down the path (which is no longer preferred)
      // thus, we detach
      val oldPreferred = w.split
      oldPreferred.foreach(_.setPathParent(w))

      // v is deeper that w, thus, it goes right
      w.setRight(v)
      v.clearPathParent() // v now is directly connected (in the aux-tree represenation) to its parent
      v.splay() // splay, will make the new v.pathparent to be that of w.pathparent (if present)
    }
  }

  /**
    * Cut an element from the tree it belongs, making it and its children
    * a new rooted tree.
    *
    * @param v
    */
  def cut(v: Vertex) = {
    access(v)
    v.splitLeft
    roots += v
    //    val reduceBy = v.node.key
    //    v.node.globalUpdateKey(_ - reduceBy)
  }

  /**
    * Make a link between two nodes. Each vertex must be
    * in different trees. Child must be its root.
    *
    * @param child
    * @param parent
    */
  override def link(child: Vertex, parent: Vertex): Unit = {
    roots.remove(child)
    access(parent)
    child.setLeft(parent)
  }

}

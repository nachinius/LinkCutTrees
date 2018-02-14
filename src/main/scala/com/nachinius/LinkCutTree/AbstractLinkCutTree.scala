package com.nachinius.LinkCutTree

import com.nachinius.splay.Node
import scala.collection.mutable

/**
  * A forest (a set) of rooted (unordered) trees
  * @tparam A
  */
abstract class AbstractLinkCutTree[A]() extends InnerClassesLinkCutTree[A] {

  /**
    * create a new tree in the forest
    * @param elem associated data to the vertex
    */
  def makeTree(elem: A): Vertex

  /**
    * Link both vertices, they must belong to different trees.
    *
    * @param child
    * @param parent
    */
  def link(child: Vertex, parent: Vertex)

  /**
    * Delete the edge that goes from v to its parent
    * @param v
    */
  def cut(v: Vertex)

  /**
    * Find the root of the tree in which `v` belongs.
    * @param v
    */
  def findRoot(v: Vertex): Vertex

  def pathAggregate[U](v: Vertex, f: A => U): Unit
}







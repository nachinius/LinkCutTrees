package com.nachinius.LinkCutTree

import com.nachinius.splay.Node

trait InnerClassesLinkCutTree[A] {
  // inside the class, to avoid mixing vertex of different link cut tree
  case class Vertex(private[LinkCutTree] val node: Node[Int,A]) {
    self =>
    def getElement: A = node.elem
    def splay() = {
      node.splay
      ()
    }
    def split: Option[Vertex] = node.split.map(Vertex)
    def splitLeft: Option[Vertex] = node.splitLeft.map(Vertex)
    def setPathParent(v: Vertex): Unit = {
      self.node.externalParent = v.node.asOption
      self.node.parent = None
    }
    def clearPathParent(): Unit = self.node.externalParent = None
    def pathParent: Option[Vertex] = self.node.externalParent.map(Vertex)
    def setRight(v: Vertex) = {
      self.node.setRight(v.node.asOption)
    }
    def setLeft(v: Vertex) = {
      self.node.setLeft(v.node.asOption)
    }
    def leftist = node.leftist
  }


}

package com.nachinius.LinkCutTree

import org.scalatest.{FreeSpec, Matchers, OptionValues}

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class LinkCutTreeTest extends FreeSpec with Matchers with OptionValues {

  "makeTree should work" in {
    val ts = new LinkCutTree[String]
    val vertex = ts.makeTree("hello")
  }

  "findRoot" - {
    "on a vertex that is a single node tree is itself" in {
      val ts = new LinkCutTree[String]
      val v1 = ts.makeTree("hello")
      val v2 = ts.makeTree("world")
      ts.findRoot(v1) shouldBe v1
      ts.findRoot(v2) shouldBe v2
    }
  }
  "Link two vertices" - {
    val ts = new LinkCutTree[String]
    val v1 = ts.makeTree("hello")
    val v2 = ts.makeTree("world")
    ts.link(v1, v2)
    "should make them part of the same tree (have the same root)" in {
      ts.findRoot(v1) shouldBe ts.findRoot(v2)
      ts.findRoot(v1) shouldBe v2
    }
    "and one root should dissapear from the list" in {
      ts.roots should contain only v2
    }
  }
  "Cut a vertex from a tree" - {
    val ts = new LinkCutTree[String]
    val v1 = ts.makeTree("hello")
    val v2 = ts.makeTree("world")
    val v3 = ts.makeTree("hello2")
    val v4 = ts.makeTree("world2")
    ts.link(v2, v1)
    ts.link(v3, v1)
    ts.link(v4, v3)
    ts.roots.size shouldBe 1
    ts.cut(v3)
    ts.roots.size shouldBe 2
    ts.findRoot(v4) shouldBe ts.findRoot(v3)
    ts.findRoot(v3) shouldBe v3
  }
  "pathAggreagate" - {
    "should go trough all elements in path" in {
      val ts = new LinkCutTree[Int]
      val rnd = new Random(279230556L)
      val size = 100
      // created isolated ndoes
      val vertices = (0 until size).map(n => ts.makeTree(n)).toVector
      // randomly connect them, keeping track of vertices indexes
      val connections = rnd.shuffle(vertices.zipWithIndex.tail).map {
        case (vertex: ts.Vertex, i: Int) =>
          val connectingTo = rnd.nextInt(i) // only connect (as child) to a previously created parent, warrants that we have a tree
        val connectTo = vertices(connectingTo)
          ts.link(vertex, connectTo)
          (i, connectingTo)
      }.toMap

      // helps track a path given the original information
      def collect(acc: List[Int]): List[Int] = {
        connections.get(acc.head) match {
          case None => acc
          case Some(n) => collect(n :: acc)
        }
      }

      //test
      val acc = new ArrayBuffer[Int](size)
      ts.pathAggregate(vertices.last, {
        n: Int => acc += n
      })
      acc.toList should contain theSameElementsInOrderAs collect(size - 1 :: Nil)
    }
  }
}

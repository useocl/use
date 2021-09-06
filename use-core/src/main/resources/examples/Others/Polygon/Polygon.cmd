-- Opens the class diagram:
-- open examples/Others/Polygon/Polygon.use

-- Creates the object diagram:
-- read examples/Others/Polygon/Polygon.cmd

!create pg : Polygon

!create p1 : Point
!set p1.x := 0.0
!set p1.y := 0.0
!insert (pg, p1) into Polygon_Point

!create p2 : Point
!set p2.x := 3.0
!set p2.y := 0.0
!insert (pg, p2) into Polygon_Point

!create p3 : ColorPoint
!set p3.x := 1.5
!set p3.y := 1.5
!set p3.color := 'red'
!insert (pg, p3) into Polygon_Point

!openter pg translate(-1.5, 0)
!openter p1 translate(dx, dy)
!set self.x := self.x + dx
!set self.y := self.y + dy
!opexit
!openter p2 translate(dx, dy)
!set self.x := self.x + dx
!set self.y := self.y + dy
!opexit
!openter p3 translate(dx, dy)
!set self.x := self.x + dx
!set self.y := self.y + dy
!opexit
!opexit

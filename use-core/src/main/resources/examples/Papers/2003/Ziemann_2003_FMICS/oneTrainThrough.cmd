-- Opens the class diagram:
-- open examples/Papers/2003/Ziemann_2003_FMICS/bart.use

-- Creates the object diagram:
-- read examples/Papers/2003/Ziemann_2003_FMICS/oneTrainThrough.cmd

-- create segments and gates

!create DalyCity : StationPlatform 
!create Seg_6640 : Segment
!create Gate_6741 : Gate
!create Seg_6741 : Segment
!create Gate_7588 : Gate
!create Seg_7588 : Segment
!create Seg_8522 : Segment
!create Seg_8722 : Segment
!create Seg_9003 : Segment
!create Seg_9509 : Segment
!create Seg_11355 : Segment
!create Gate_11900 : Gate
!create Seg_11900 : Segment
!create Seg_12369 : Segment
!create Gate_12369 : Gate
!create Seg_12500 : Segment
!create Seg_13100 : Segment
!create Seg_13400 : Segment
!create Seg_14190 : Segment
!create Seg_14500 : Segment
!create Seg_14850 : Segment
!create Seg_15150 : Segment
!create BalboaPark : StationPlatform
!create Seg_16110 : Segment
!create Seg_16140 : Segment
!create Seg_16500 : Segment
!create Seg_17000 : Segment
!create Seg_17025 : Segment
!create Seg_17250 : Segment
!create Seg_18075 : Segment
!create Seg_18218 : Segment
!create Seg_19245 : Segment
!create Seg_19550 : Segment
!create Seg_20550 : Segment
!create Seg_20795 : Segment
!create Seg_21295 : Segment
!create GlenPark : StationPlatform
!create Seg_22185 : Segment
!create Seg_22420 : Segment
!create Seg_22780 : Segment
!create Seg_24950 : Segment
!create Seg_25635 : Segment
!create Seg_28025 : Segment
!create Seg_28115 : Segment
!create Seg_28374 : Segment
!create Seg_29025 : Segment
!create Seg_29835 : Segment
!create Seg_30065 : Segment
!create Gate_30080 : Gate
!create Seg_30080 : Segment
!create Mission24th : StationPlatform

-- connect segments and gates

!insert (DalyCity,Seg_6640) into SegmentOrder

!insert (Seg_6640,Seg_6741) into SegmentOrder
!insert (Gate_6741,Seg_6640) into Boundary
!insert (Seg_6741,Seg_7588) into SegmentOrder
!insert (Gate_7588,Seg_6741) into Boundary
!insert (Seg_7588,Seg_8522) into SegmentOrder
!insert (Seg_8522,Seg_8722) into SegmentOrder
!insert (Seg_8722,Seg_9003) into SegmentOrder
!insert (Seg_9003,Seg_9509) into SegmentOrder
!insert (Seg_9509,Seg_11355) into SegmentOrder
!insert (Seg_11355,Seg_11900) into SegmentOrder
!insert (Gate_11900,Seg_11355) into Boundary
!insert (Seg_11900,Seg_12369) into SegmentOrder
!insert (Gate_12369,Seg_11900) into Boundary
!insert (Seg_12369,Seg_12500) into SegmentOrder
!insert (Seg_12500,Seg_13100) into SegmentOrder
!insert (Seg_13100,Seg_13400) into SegmentOrder
!insert (Seg_13400,Seg_14190) into SegmentOrder
!insert (Seg_14190,Seg_14500) into SegmentOrder
!insert (Seg_14500,Seg_14850) into SegmentOrder
!insert (Seg_14850,Seg_15150) into SegmentOrder
!insert (Seg_15150,BalboaPark) into SegmentOrder
!insert (BalboaPark,Seg_16110) into SegmentOrder
!insert (Seg_16110,Seg_16140) into SegmentOrder
!insert (Seg_16140,Seg_16500) into SegmentOrder
!insert (Seg_16500,Seg_17000) into SegmentOrder
!insert (Seg_17000,Seg_17025) into SegmentOrder
!insert (Seg_17025,Seg_17250) into SegmentOrder
!insert (Seg_17250,Seg_18075) into SegmentOrder
!insert (Seg_18075,Seg_18218) into SegmentOrder
!insert (Seg_18218,Seg_19245) into SegmentOrder
!insert (Seg_19245,Seg_19550) into SegmentOrder
!insert (Seg_19550,Seg_20550) into SegmentOrder
!insert (Seg_20550,Seg_20795) into SegmentOrder
!insert (Seg_20795,Seg_21295) into SegmentOrder
!insert (Seg_21295,GlenPark) into SegmentOrder
!insert (GlenPark,Seg_22185) into SegmentOrder
!insert (Seg_22185,Seg_22420) into SegmentOrder
!insert (Seg_22420,Seg_22780) into SegmentOrder
!insert (Seg_22780,Seg_24950) into SegmentOrder
!insert (Seg_24950,Seg_25635) into SegmentOrder
!insert (Seg_25635,Seg_28025) into SegmentOrder
!insert (Seg_28025,Seg_28115) into SegmentOrder
!insert (Seg_28115,Seg_28374) into SegmentOrder
!insert (Seg_28374,Seg_29025) into SegmentOrder
!insert (Seg_29025,Seg_29835) into SegmentOrder
!insert (Seg_29835,Seg_30065) into SegmentOrder
!insert (Seg_30065,Seg_30080) into SegmentOrder
!insert (Gate_30080,Seg_30065) into Boundary
!insert (Seg_30080,Mission24th) into SegmentOrder

-- create tracks

!create track1 : Track

-- assign segments to a track

!insert (DalyCity,track1) into BelongsTo
!insert (Seg_6640,track1) into BelongsTo
!insert (Seg_6741,track1) into BelongsTo
!insert (Seg_7588,track1) into BelongsTo
!insert (Seg_8522,track1) into BelongsTo
!insert (Seg_8722,track1) into BelongsTo
!insert (Seg_9003,track1) into BelongsTo
!insert (Seg_9509,track1) into BelongsTo
!insert (Seg_11355,track1) into BelongsTo
!insert (Seg_11900,track1) into BelongsTo
!insert (Seg_12369,track1) into BelongsTo
!insert (Seg_12500,track1) into BelongsTo
!insert (Seg_13100,track1) into BelongsTo
!insert (Seg_13400,track1) into BelongsTo
!insert (Seg_14190,track1) into BelongsTo
!insert (Seg_14500,track1) into BelongsTo
!insert (Seg_14850,track1) into BelongsTo
!insert (Seg_15150,track1) into BelongsTo
!insert (BalboaPark,track1) into BelongsTo
!insert (Seg_16110,track1) into BelongsTo
!insert (Seg_16140,track1) into BelongsTo
!insert (Seg_16500,track1) into BelongsTo
!insert (Seg_17000,track1) into BelongsTo
!insert (Seg_17025,track1) into BelongsTo
!insert (Seg_17250,track1) into BelongsTo
!insert (Seg_18075,track1) into BelongsTo
!insert (Seg_18218,track1) into BelongsTo
!insert (Seg_19245,track1) into BelongsTo
!insert (Seg_19550,track1) into BelongsTo
!insert (Seg_20550,track1) into BelongsTo
!insert (Seg_20795,track1) into BelongsTo
!insert (Seg_21295,track1) into BelongsTo
!insert (GlenPark,track1) into BelongsTo
!insert (Seg_22185,track1) into BelongsTo
!insert (Seg_22420,track1) into BelongsTo
!insert (Seg_22780,track1) into BelongsTo
!insert (Seg_24950,track1) into BelongsTo
!insert (Seg_25635,track1) into BelongsTo
!insert (Seg_28025,track1) into BelongsTo
!insert (Seg_28115,track1) into BelongsTo
!insert (Seg_28374,track1) into BelongsTo
!insert (Seg_29025,track1) into BelongsTo
!insert (Seg_29835,track1) into BelongsTo
!insert (Seg_30065,track1) into BelongsTo
!insert (Seg_30080,track1) into BelongsTo
!insert (Mission24th,track1) into BelongsTo

-- create stationcomputers

!create sc1 : StationComputer
!create sc2 : StationComputer
!create sc3 : StationComputer

-- define bounderies of stationcomputers

!insert (sc1,DalyCity) into ControlBegin
!insert (sc1,Seg_8722) into ControlEnd
!insert (sc2,Seg_9003) into ControlBegin
!insert (sc2,Seg_28025) into ControlEnd
!insert (sc3,Seg_28115) into ControlBegin
!insert (sc3,Mission24th) into ControlEnd

-- create trains

!create  Choochoo : Train
-- !create  Chattanooga : Train

-- insert links of trains

!insert (DalyCity,Choochoo) into Origin
!insert (Mission24th,Choochoo) into Destination
!insert (Choochoo,track1) into On

-- !insert (BalboaPark,Chattanooga) into Origin
-- !insert (Mission24th,Chattanooga) into Destination
-- !insert (Chattanooga,track1) into On
-- set attributes of trains

!set Choochoo.nose := 6640
!set Choochoo.v := 0
!set Choochoo.a := 0
!set Choochoo.vcm := 0
!set Choochoo.acm := 0
!set Choochoo.length := 710

-- !set Chattanooga.nose := 21943.55969949475
-- !set Chattanooga.v := 0
-- !set Chattanooga.a := 0
-- !set Chattanooga.vcm := 0
-- !set Chattanooga.acm := 0
-- !set Chattanooga.length := 710
-- set attributes of segments and gates

!set DalyCity.segBegin := 5940
!set DalyCity.segEnd := 6640
!set DalyCity.length := 700
!set DalyCity.civilSpeed := 36
!set DalyCity.grade := -0.8
!set DalyCity.exposure := #open
!set DalyCity.stationName := 'Daly City Station Platform'

!set Seg_6640.segBegin := 6640
!set Seg_6640.segEnd := 6741
!set Seg_6640.length := 101
!set Seg_6640.civilSpeed := 36
!set Seg_6640.grade := -0.8
!set Seg_6640.exposure := #open

!set Gate_6741.open := true

!set Seg_6741.segBegin := 6741
!set Seg_6741.segEnd := 7588
!set Seg_6741.length := 847
!set Seg_6741.civilSpeed := 36
!set Seg_6741.grade := -0.8
!set Seg_6741.exposure := #open

!set Gate_7588.open := true

!set Seg_7588.segBegin := 7588
!set Seg_7588.segEnd := 8522
!set Seg_7588.length := 934
!set Seg_7588.civilSpeed := 36
!set Seg_7588.grade := -0.8
!set Seg_7588.exposure := #open

!set Seg_8522.segBegin := 8522
!set Seg_8522.segEnd := 8722
!set Seg_8522.length := 200
!set Seg_8522.civilSpeed := 80
-- transition:
!set Seg_8522.grade := 0.96
!set Seg_8522.exposure := #open

!set Seg_8722.segBegin := 8722
!set Seg_8722.segEnd := 9003
!set Seg_8722.length := 281
!set Seg_8722.civilSpeed := 80
!set Seg_8722.grade := 2.75
!set Seg_8722.exposure := #open

!set Seg_9003.segBegin := 9003
!set Seg_9003.segEnd := 9509
!set Seg_9003.length := 506
!set Seg_9003.civilSpeed := 50
!set Seg_9003.grade := 2.75
!set Seg_9003.exposure := #open

!set Seg_9509.segBegin := 9509
!set Seg_9509.segEnd := 11355
!set Seg_9509.length := 1846
!set Seg_9509.civilSpeed := 50
!set Seg_9509.grade := -0.38
!set Seg_9509.exposure := #open

!set Seg_11355.segBegin := 11355
!set Seg_11355.segEnd := 11900
!set Seg_11355.length := 545
!set Seg_11355.civilSpeed := 50
!set Seg_11355.grade := -3.5
!set Seg_11355.exposure := #open

!set Gate_11900.open := true

!set Seg_11900.segBegin := 11900
!set Seg_11900.segEnd := 12369
!set Seg_11900.length := 469
!set Seg_11900.civilSpeed := 80
!set Seg_11900.grade := -3.5
!set Seg_11900.exposure := #open

!set Gate_12369.open := true

!set Seg_12369.segBegin := 12369
!set Seg_12369.segEnd := 12500
!set Seg_12369.length := 131
!set Seg_12369.civilSpeed := 80
!set Seg_12369.grade := -3.5
!set Seg_12369.exposure := #open

!set Seg_12500.segBegin := 12500
!set Seg_12500.segEnd := 13100
!set Seg_12500.length := 600
!set Seg_12500.civilSpeed := 80
-- transition:
!set Seg_12500.grade := -2.81
!set Seg_12500.exposure := #open

!set Seg_13100.segBegin := 13100
!set Seg_13100.segEnd := 13400
!set Seg_13100.length := 300
!set Seg_13100.civilSpeed := 80
!set Seg_13100.grade := -2.11
!set Seg_13100.exposure := #open

!set Seg_13400.segBegin := 13400
!set Seg_13400.segEnd := 14190
!set Seg_13400.length := 790
!set Seg_13400.civilSpeed := 80
-- transition:
!set Seg_13400.grade := -1.75
!set Seg_13400.exposure := #open

!set Seg_14190.segBegin := 14190
!set Seg_14190.segEnd := 14500
!set Seg_14190.length := 310
!set Seg_14190.civilSpeed := 70
-- transition:
!set Seg_14190.grade := -1.39
!set Seg_14190.exposure := #tunnel

!set Seg_14500.segBegin := 14500
!set Seg_14500.segEnd := 14850
!set Seg_14500.length := 350
!set Seg_14500.civilSpeed := 70
!set Seg_14500.grade := -3.19
!set Seg_14500.exposure := #tunnel

!set Seg_14850.segBegin := 14850
!set Seg_14850.segEnd := 15150
!set Seg_14850.length := 300
!set Seg_14850.civilSpeed := 70
!set Seg_14850.grade := 2.1
!set Seg_14850.exposure := #tunnel

!set Seg_15150.segBegin := 15150
!set Seg_15150.segEnd := 15410
!set Seg_15150.length := 260
!set Seg_15150.civilSpeed := 70
!set Seg_15150.grade := -1
!set Seg_15150.exposure := #tunnel

!set BalboaPark.segBegin := 15410
!set BalboaPark.segEnd := 16110
!set BalboaPark.length := 700
!set BalboaPark.civilSpeed := 36
!set BalboaPark.grade := -1
!set BalboaPark.exposure := #tunnel
!set BalboaPark.stationName := 'Balboa Park Station Platform'

!set Seg_16110.segBegin := 16110
!set Seg_16110.segEnd := 16140
!set Seg_16110.length := 30
!set Seg_16110.civilSpeed := 36
!set Seg_16110.grade := -1
!set Seg_16110.exposure := #tunnel

!set Seg_16140.segBegin := 16140
!set Seg_16140.segEnd := 16500
!set Seg_16140.length := 360
!set Seg_16140.civilSpeed := 50
!set Seg_16140.grade := -1
!set Seg_16140.exposure := #tunnel

!set Seg_16500.segBegin := 16500
!set Seg_16500.segEnd := 17000
!set Seg_16500.length := 500
!set Seg_16500.civilSpeed := 50
-- transition:
!set Seg_16500.grade := 0.56
!set Seg_16500.exposure := #tunnel

!set Seg_17000.segBegin := 17000
!set Seg_17000.segEnd := 17025
!set Seg_17000.length := 25
!set Seg_17000.civilSpeed := 50
!set Seg_17000.grade := 2.11
!set Seg_17000.exposure := #tunnel

!set Seg_17025.segBegin := 17025
!set Seg_17025.segEnd := 17250
!set Seg_17025.length := 225
!set Seg_17025.civilSpeed := 50
!set Seg_17025.grade := 2.11
!set Seg_17025.exposure := #open

!set Seg_17250.segBegin := 17250
!set Seg_17250.segEnd := 18075
!set Seg_17250.length := 825
!set Seg_17250.civilSpeed := 50
-- transition:
!set Seg_17250.grade := 0.89
!set Seg_17250.exposure := #open

!set Seg_18075.segBegin := 18075
!set Seg_18075.segEnd := 18218
!set Seg_18075.length := 143
!set Seg_18075.civilSpeed := 50
!set Seg_18075.grade := -3.89
!set Seg_18075.exposure := #open

!set Seg_18218.segBegin := 18218
!set Seg_18218.segEnd := 19245
!set Seg_18218.length := 1027
!set Seg_18218.civilSpeed := 70
!set Seg_18218.grade := -3.89
!set Seg_18218.exposure := #open

!set Seg_19245.segBegin := 19245
!set Seg_19245.segEnd := 19550
!set Seg_19245.length := 305
!set Seg_19245.civilSpeed := 70
!set Seg_19245.grade := -3.89
!set Seg_19245.exposure := #tunnel

!set Seg_19550.segBegin := 19550
!set Seg_19550.segEnd := 20550
!set Seg_19550.length := 1000
!set Seg_19550.civilSpeed := 70
-- transition:
!set Seg_19550.grade := -0.86
!set Seg_19550.exposure := #tunnel

!set Seg_20550.segBegin := 20550
!set Seg_20550.segEnd := 20795
!set Seg_20550.length := 245
!set Seg_20550.civilSpeed := 70
!set Seg_20550.grade := 2.14
!set Seg_20550.exposure := #tunnel

!set Seg_20795.segBegin := 20795
!set Seg_20795.segEnd := 21295
!set Seg_20795.length := 500
!set Seg_20795.civilSpeed := 70
-- transition:
!set Seg_20795.grade := 1.22
!set Seg_20795.exposure := #tunnel

!set Seg_21295.segBegin := 21295
!set Seg_21295.segEnd := 21485
!set Seg_21295.length := 190
!set Seg_21295.civilSpeed := 70
!set Seg_21295.grade := 0.3
!set Seg_21295.exposure := #tunnel

!set GlenPark.segBegin := 21485
!set GlenPark.segEnd := 22185
!set GlenPark.length := 700
!set GlenPark.civilSpeed := 36
!set GlenPark.grade := 0.3
!set GlenPark.exposure := #tunnel
!set GlenPark.stationName := 'Glen Park Station Platform'

!set Seg_22185.segBegin := 22185
!set Seg_22185.segEnd := 22420
!set Seg_22185.length := 235
!set Seg_22185.civilSpeed := 80
!set Seg_22185.grade := 0.3
!set Seg_22185.exposure := #tunnel

!set Seg_22420.segBegin := 22420
!set Seg_22420.segEnd := 22780
!set Seg_22420.length := 360
!set Seg_22420.civilSpeed := 80
-- transition:
!set Seg_22420.grade := -0.19
!set Seg_22420.exposure := #tunnel

!set Seg_22780.segBegin := 22780
!set Seg_22780.segEnd := 24950
!set Seg_22780.length := 2170
!set Seg_22780.civilSpeed := 80
!set Seg_22780.grade := -0.68
!set Seg_22780.exposure := #tunnel

!set Seg_24950.segBegin := 24950
!set Seg_24950.segEnd := 25635
!set Seg_24950.length := 685
!set Seg_24950.civilSpeed := 80
-- transition:
!set Seg_24950.grade := -1.9
!set Seg_24950.exposure := #tunnel

!set Seg_25635.segBegin := 25635
!set Seg_25635.segEnd := 28025
!set Seg_25635.length := 2390
!set Seg_25635.civilSpeed := 80
!set Seg_25635.grade := -3.12
!set Seg_25635.exposure := #tunnel

!set Seg_28025.segBegin := 28025
!set Seg_28025.segEnd := 28115
!set Seg_28025.length := 90
!set Seg_28025.civilSpeed := 80
-- transition:
!set Seg_28025.grade := -2.2
!set Seg_28025.exposure := #tunnel

!set Seg_28115.segBegin := 28115
!set Seg_28115.segEnd := 28374
!set Seg_28115.length := 259
!set Seg_28115.civilSpeed := 80
-- transition:
!set Seg_28115.grade := -1.29
!set Seg_28115.exposure := #tunnel

!set Seg_28374.segBegin := 28374
!set Seg_28374.segEnd := 29025
!set Seg_28374.length := 651
!set Seg_28374.civilSpeed := 63
-- transition:
!set Seg_28374.grade := -0.37
!set Seg_28374.exposure := #tunnel

!set Seg_29025.segBegin := 29025
!set Seg_29025.segEnd := 29835
!set Seg_29025.length := 810
!set Seg_29025.civilSpeed := 63
!set Seg_29025.grade := 0.55
!set Seg_29025.exposure := #tunnel

!set Seg_29835.segBegin := 29835
!set Seg_29835.segEnd := 30065
!set Seg_29835.length := 230
!set Seg_29835.civilSpeed := 63
-- transition:
!set Seg_29835.grade := 0.13
!set Seg_29835.exposure := #tunnel

!set Seg_30065.segBegin := 30065
!set Seg_30065.segEnd := 30080
!set Seg_30065.length := 15
!set Seg_30065.civilSpeed := 63
!set Seg_30065.grade := -0.3
!set Seg_30065.exposure := #tunnel

!set Gate_30080.open := true

!set Seg_30080.segBegin := 30080
!set Seg_30080.segEnd := 32281
!set Seg_30080.length := 2201
!set Seg_30080.civilSpeed := 63
!set Seg_30080.grade := -0.3
!set Seg_30080.exposure := #tunnel

!set Mission24th.segBegin := 32281
!set Mission24th.segEnd := 32981
!set Mission24th.length := 700
!set Mission24th.civilSpeed := 36
!set Mission24th.grade := -0.3
!set Mission24th.exposure := #tunnel
!set Mission24th.stationName := '24-th Street Mission Station Platform'

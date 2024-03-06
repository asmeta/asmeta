; ModuleID = 'increment.cpp'
source_filename = "increment.cpp"
target datalayout = "e-m:e-p270:32:32-p271:32:32-p272:64:64-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-pc-linux-gnu"

%"class.std::ios_base::Init" = type { i8 }
%class.increment = type { [2 x i32] }

@_ZStL8__ioinit = internal global %"class.std::ios_base::Init" zeroinitializer, align 1
@__dso_handle = external hidden global i8
@llvm.global_ctors = appending global [1 x { i32, void ()*, i8* }] [{ i32, void ()*, i8* } { i32 65535, void ()* @_GLOBAL__sub_I_increment.cpp, i8* null }]

@_ZN9incrementC1Ev = dso_local unnamed_addr alias void (%class.increment*), void (%class.increment*)* @_ZN9incrementC2Ev

; Function Attrs: noinline uwtable
define internal void @__cxx_global_var_init() #0 section ".text.startup" {
  call void @_ZNSt8ios_base4InitC1Ev(%"class.std::ios_base::Init"* noundef nonnull align 1 dereferenceable(1) @_ZStL8__ioinit)
  %1 = call i32 @__cxa_atexit(void (i8*)* bitcast (void (%"class.std::ios_base::Init"*)* @_ZNSt8ios_base4InitD1Ev to void (i8*)*), i8* getelementptr inbounds (%"class.std::ios_base::Init", %"class.std::ios_base::Init"* @_ZStL8__ioinit, i32 0, i32 0), i8* @__dso_handle) #3
  ret void
}

declare void @_ZNSt8ios_base4InitC1Ev(%"class.std::ios_base::Init"* noundef nonnull align 1 dereferenceable(1)) unnamed_addr #1

; Function Attrs: nounwind
declare void @_ZNSt8ios_base4InitD1Ev(%"class.std::ios_base::Init"* noundef nonnull align 1 dereferenceable(1)) unnamed_addr #2

; Function Attrs: nounwind
declare i32 @__cxa_atexit(void (i8*)*, i8*, i8*) #3

; Function Attrs: mustprogress noinline nounwind optnone uwtable
define dso_local void @_ZN9increment10r_main_seqEv(%class.increment* noundef nonnull align 4 dereferenceable(8) %0) #4 align 2 {
  %2 = alloca %class.increment*, align 8
  store %class.increment* %0, %class.increment** %2, align 8
  %3 = load %class.increment*, %class.increment** %2, align 8
  %4 = getelementptr inbounds %class.increment, %class.increment* %3, i32 0, i32 0
  %5 = getelementptr inbounds [2 x i32], [2 x i32]* %4, i64 0, i64 0
  %6 = load i32, i32* %5, align 4
  %7 = icmp slt i32 %6, 100
  br i1 %7, label %8, label %15

8:                                                ; preds = %1
  %9 = getelementptr inbounds %class.increment, %class.increment* %3, i32 0, i32 0
  %10 = getelementptr inbounds [2 x i32], [2 x i32]* %9, i64 0, i64 0
  %11 = load i32, i32* %10, align 4
  %12 = add nsw i32 %11, 1
  %13 = getelementptr inbounds %class.increment, %class.increment* %3, i32 0, i32 0
  %14 = getelementptr inbounds [2 x i32], [2 x i32]* %13, i64 0, i64 1
  store i32 %12, i32* %14, align 4
  br label %15

15:                                               ; preds = %8, %1
  ret void
}

; Function Attrs: mustprogress noinline nounwind optnone uwtable
define dso_local void @_ZN9increment6r_mainEv(%class.increment* noundef nonnull align 4 dereferenceable(8) %0) #4 align 2 {
  %2 = alloca %class.increment*, align 8
  store %class.increment* %0, %class.increment** %2, align 8
  %3 = load %class.increment*, %class.increment** %2, align 8
  %4 = getelementptr inbounds %class.increment, %class.increment* %3, i32 0, i32 0
  %5 = getelementptr inbounds [2 x i32], [2 x i32]* %4, i64 0, i64 0
  %6 = load i32, i32* %5, align 4
  %7 = icmp slt i32 %6, 100
  br i1 %7, label %8, label %15

8:                                                ; preds = %1
  %9 = getelementptr inbounds %class.increment, %class.increment* %3, i32 0, i32 0
  %10 = getelementptr inbounds [2 x i32], [2 x i32]* %9, i64 0, i64 0
  %11 = load i32, i32* %10, align 4
  %12 = add nsw i32 %11, 1
  %13 = getelementptr inbounds %class.increment, %class.increment* %3, i32 0, i32 0
  %14 = getelementptr inbounds [2 x i32], [2 x i32]* %13, i64 0, i64 1
  store i32 %12, i32* %14, align 4
  br label %15

15:                                               ; preds = %8, %1
  ret void
}

; Function Attrs: noinline nounwind optnone uwtable
define dso_local void @_ZN9incrementC2Ev(%class.increment* noundef nonnull align 4 dereferenceable(8) %0) unnamed_addr #5 align 2 {
  %2 = alloca %class.increment*, align 8
  store %class.increment* %0, %class.increment** %2, align 8
  %3 = load %class.increment*, %class.increment** %2, align 8
  %4 = getelementptr inbounds %class.increment, %class.increment* %3, i32 0, i32 0
  %5 = getelementptr inbounds [2 x i32], [2 x i32]* %4, i64 0, i64 1
  store i32 0, i32* %5, align 4
  %6 = getelementptr inbounds %class.increment, %class.increment* %3, i32 0, i32 0
  %7 = getelementptr inbounds [2 x i32], [2 x i32]* %6, i64 0, i64 0
  store i32 0, i32* %7, align 4
  ret void
}

; Function Attrs: mustprogress noinline nounwind optnone uwtable
define dso_local void @_ZN9increment27initControlledWithMonitoredEv(%class.increment* noundef nonnull align 4 dereferenceable(8) %0) #4 align 2 {
  %2 = alloca %class.increment*, align 8
  store %class.increment* %0, %class.increment** %2, align 8
  %3 = load %class.increment*, %class.increment** %2, align 8
  ret void
}

; Function Attrs: mustprogress noinline nounwind optnone uwtable
define dso_local void @_ZN9increment13fireUpdateSetEv(%class.increment* noundef nonnull align 4 dereferenceable(8) %0) #4 align 2 {
  %2 = alloca %class.increment*, align 8
  store %class.increment* %0, %class.increment** %2, align 8
  %3 = load %class.increment*, %class.increment** %2, align 8
  %4 = getelementptr inbounds %class.increment, %class.increment* %3, i32 0, i32 0
  %5 = getelementptr inbounds [2 x i32], [2 x i32]* %4, i64 0, i64 1
  %6 = load i32, i32* %5, align 4
  %7 = getelementptr inbounds %class.increment, %class.increment* %3, i32 0, i32 0
  %8 = getelementptr inbounds [2 x i32], [2 x i32]* %7, i64 0, i64 0
  store i32 %6, i32* %8, align 4
  ret void
}

; Function Attrs: mustprogress noinline norecurse optnone uwtable
define dso_local noundef i32 @main() #6 {
  %1 = alloca i32, align 4
  %2 = alloca %class.increment, align 4
  %3 = alloca i32, align 4
  store i32 0, i32* %1, align 4
  call void @_ZN9incrementC1Ev(%class.increment* noundef nonnull align 4 dereferenceable(8) %2)
  store i32 0, i32* %3, align 4
  br label %4

4:                                                ; preds = %8, %0
  %5 = load i32, i32* %3, align 4
  %6 = icmp slt i32 %5, 100
  br i1 %6, label %7, label %11

7:                                                ; preds = %4
  call void @_ZN9increment6r_mainEv(%class.increment* noundef nonnull align 4 dereferenceable(8) %2)
  br label %8

8:                                                ; preds = %7
  %9 = load i32, i32* %3, align 4
  %10 = add nsw i32 %9, 1
  store i32 %10, i32* %3, align 4
  br label %4, !llvm.loop !6

11:                                               ; preds = %4
  %12 = load i32, i32* %1, align 4
  ret i32 %12
}

; Function Attrs: noinline uwtable
define internal void @_GLOBAL__sub_I_increment.cpp() #0 section ".text.startup" {
  call void @__cxx_global_var_init()
  ret void
}

attributes #0 = { noinline uwtable "frame-pointer"="all" "min-legal-vector-width"="0" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" }
attributes #1 = { "frame-pointer"="all" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" }
attributes #2 = { nounwind "frame-pointer"="all" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" }
attributes #3 = { nounwind }
attributes #4 = { mustprogress noinline nounwind optnone uwtable "frame-pointer"="all" "min-legal-vector-width"="0" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" }
attributes #5 = { noinline nounwind optnone uwtable "frame-pointer"="all" "min-legal-vector-width"="0" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" }
attributes #6 = { mustprogress noinline norecurse optnone uwtable "frame-pointer"="all" "min-legal-vector-width"="0" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" }

!llvm.module.flags = !{!0, !1, !2, !3, !4}
!llvm.ident = !{!5}

!0 = !{i32 1, !"wchar_size", i32 4}
!1 = !{i32 7, !"PIC Level", i32 2}
!2 = !{i32 7, !"PIE Level", i32 2}
!3 = !{i32 7, !"uwtable", i32 1}
!4 = !{i32 7, !"frame-pointer", i32 2}
!5 = !{!"Ubuntu clang version 14.0.0-1ubuntu1.1"}
!6 = distinct !{!6, !7}
!7 = !{!"llvm.loop.mustprogress"}

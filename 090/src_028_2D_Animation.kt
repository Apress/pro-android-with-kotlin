val springAnim = SpringAnimation(tv, DynamicAnimation.TRANSLATION_X, 500.0f).apply {
    setStartVelocity(1.0f)
    spring.stiffness = SpringForce.STIFFNESS_LOW
    spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
}
springAnim.start()

val fragmentShaderCode = """
precision mediump float;
varying vec4 fColor;
varying vec3 N;
varying vec3 v;
uniform vec4 lightPos;

void main() {
vec3 L = normalize(lightPos.xyz - v);
vec3 E = normalize(-v); // eye coordinates!
vec3 R = normalize(-reflect(L,N));

//calculate Ambient Term:
vec4 Iamb = vec4(0.0, 0.1, 0.1, 1.0);

//calculate Diffuse Term:
vec4 Idiff = vec4(0.0, 0.0, 1.0, 1.0) *
max(dot(N,L), 0.0);
Idiff = clamp(Idiff, 0.0, 1.0);

// calculate Specular Term:
vec4 Ispec = vec4(1.0, 1.0, 0.5, 1.0) *
pow(max(dot(R,E),0.0),
/*shininess=*/5.0);
Ispec = clamp(Ispec, 0.0, 1.0);

// write Total Color:
gl_FragColor = Iamb + Idiff + Ispec;
//gl_FragColor = fColor; // use vertex color instead
}
""".trimIndent()

package com.example.ChatBotMentalHealth.service;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;
import java.util.Random;

@Service
public class ChatBotService {

    private final StanfordCoreNLP pipeline;
    private final Random random;

    // Anxiety responses (15 variations)
    private final List<String> anxietyResponses = List.of(
            "I understand you might be feeling anxious. Have you tried deep breathing exercises?",
            "Anxiety can be overwhelming; sometimes mindfulness can help ease the tension.",
            "When anxiety strikes, taking a moment to focus on your breath might help.",
            "It seems like anxiety is getting to you. Would you like to try a grounding exercise?",
            "Anxiety is tough. Perhaps a short walk or some calming music might help.",
            "Sometimes, jotting down your thoughts can reduce anxiety. Would you like to try that?",
            "Anxiety can make everything feel heavy. Have you considered a brief meditation?",
            "If you’re feeling anxious, a few minutes of deep breathing might be useful.",
            "Anxiety affects us all. Maybe a mindfulness practice could help calm your nerves.",
            "When anxiety is high, a distraction like reading or listening to music may help.",
            "It sounds like anxiety is present—sometimes, small breaks can relieve the pressure.",
            "Have you tried any relaxation techniques like progressive muscle relaxation?",
            "Taking a few moments to breathe deeply might help reduce your anxiety.",
            "Anxiety can be challenging; sometimes talking to someone can lighten the load.",
            "If you’re feeling nervous, focusing on a calming routine could help ease your mind."
    );

    // Stress responses (15 variations)
    private final List<String> stressResponses = List.of(
            "Managing stress is important. Would you like some tips on relaxation or time management?",
            "Stress can be draining. Perhaps a short break or some light exercise might help.",
            "It sounds like you're under a lot of stress. Have you tried prioritizing your tasks?",
            "When stress builds up, a few minutes of mindfulness might make a difference.",
            "Sometimes, stepping away for a brief walk can help clear your mind from stress.",
            "Have you considered scheduling short breaks during your day to manage stress?",
            "Stress can overwhelm us. A relaxing activity like reading or listening to calm music may help.",
            "If stress is getting high, deep breathing exercises might help reduce the tension.",
            "It might help to break down your tasks into smaller steps to manage stress better.",
            "When you feel stressed, a change of scenery—even a short one—can help reset your mood.",
            "Stress can make everything seem harder. A few minutes of quiet reflection might help.",
            "Sometimes organizing your schedule can help mitigate stress. Would that be helpful?",
            "Have you tried writing down your stressors? It can help in managing them.",
            "A calming cup of tea and a few deep breaths can sometimes reduce stress significantly.",
            "Remember to take care of yourself; small moments of relaxation can ease stress."
    );

    // Depression responses (15 variations)
    private final List<String> depressionResponses = List.of(
            "I'm sorry you're feeling this way. It might help to talk to someone you trust or a professional.",
            "Depression can feel isolating. Sometimes sharing your feelings can lighten the burden.",
            "I know it’s hard when sadness takes over. Would you like some suggestions on seeking support?",
            "It sounds like you're struggling with depression. Sometimes, talking to a friend or counselor can help.",
            "I'm really sorry you’re feeling down. Have you considered reaching out for professional help?",
            "When depression is present, small steps like a short walk or a hobby might lift your mood.",
            "It might help to talk through your feelings with someone who understands. Would that be okay?",
            "Depression can be very challenging. Would you like some information on support groups or counseling?",
            "I'm here to listen. Sometimes, expressing how you feel can be a first step toward feeling better.",
            "If you’re feeling persistently sad, it may help to speak with a mental health professional.",
            "It’s important to take care of yourself during tough times. Have you thought about self-care routines?",
            "Sometimes, creative outlets like writing or art can provide a way to express your feelings.",
            "I'm sorry you're going through this. Would you like some resources for professional support?",
            "When depression takes hold, even small acts of self-care can be significant. I'm here if you need to talk.",
            "Please remember that you’re not alone. Sharing your feelings can sometimes help ease depression."
    );

    // Greeting responses (15 variations)
    private final List<String> greetingResponses = List.of(
            "Hello there! How can I help you today?",
            "Hi! What’s on your mind?",
            "Hey there, how are you feeling today?",
            "Greetings! How may I support you?",
            "Hello! I’m here to listen. What’s bothering you?",
            "Hi, nice to meet you. How can I assist you today?",
            "Hey! How are you doing? I’m here to help.",
            "Hello! Feel free to share what’s on your mind.",
            "Hi there! I’m all ears—what’s going on?",
            "Greetings! How can I be of service today?",
            "Hey! I’m here to provide support. What would you like to talk about?",
            "Hello, I’m listening. What would you like to discuss?",
            "Hi! How can I assist you with your concerns today?",
            "Hey there! Let’s chat. What’s been troubling you?",
            "Hello! I’m here to help you. What’s on your mind?"
    );

    // Goodbye responses (15 variations)
    private final List<String> goodbyeResponses = List.of(
            "Goodbye! Remember, I’m here whenever you need someone to talk to.",
            "Take care! Feel free to come back anytime you need support.",
            "Bye for now! I’m always here if you need a listening ear.",
            "Goodbye, and remember to be kind to yourself.",
            "Farewell! I’m here to help whenever you’re ready to talk again.",
            "Take care of yourself! Reach out if you ever need to chat.",
            "Bye! I hope you find some peace and comfort today.",
            "Goodbye! Your well-being matters, and I’m always here for you.",
            "Farewell, and remember, every day is a new beginning.",
            "Goodbye! Stay strong, and remember I’m just a message away.",
            "Take care, and don’t hesitate to reach out if you need anything.",
            "Bye for now. Wishing you a peaceful day ahead.",
            "Goodbye! Remember that it’s okay to ask for help whenever you need it.",
            "Farewell! I hope our conversation brought you a bit of relief.",
            "Goodbye and take care. I’ll be here whenever you’re ready to talk again."
    );

    // Help responses (15 variations)
    private final List<String> helpResponses = List.of(
            "I’m here to support you. Can you tell me a bit more about what kind of help you need?",
            "Sure, I’m here to help. Could you please share more details about your situation?",
            "I want to help. What specific challenges are you facing right now?",
            "Let’s figure this out together. What kind of assistance do you need?",
            "I’m here for you. Can you explain a bit more about what’s troubling you?",
            "I care about you. Please tell me more about the help you’re looking for.",
            "I’m listening. Could you share more about the issues you’re dealing with?",
            "Your feelings are important. What sort of help would be most beneficial to you?",
            "I understand you need support. What specific area do you need help with?",
            "I’m here to offer assistance. Can you elaborate on your needs?",
            "Let’s work through this together. What help would you find most comforting?",
            "I’m here for you. How can I best support you right now?",
            "Please share more about what you need. I’m ready to help.",
            "I want to help you feel better. What kind of support are you seeking?",
            "I’m here to listen and help. What do you need right now?"
    );

    // Question responses (15 variations)
    private final List<String> questionResponses = List.of(
            "I’m here to answer your questions. What would you like to know more about?",
            "That’s an interesting question. Could you please elaborate so I can understand better?",
            "I appreciate your curiosity. Can you provide more details about your question?",
            "Let’s dive into that. What specifically would you like to ask?",
            "I’m ready to help. Could you clarify your question a bit more?",
            "Interesting query! What more can you tell me about your question?",
            "I’m happy to answer. Can you please expand on your inquiry?",
            "That’s a great question. Could you provide a bit more context?",
            "I want to understand fully. Can you rephrase your question?",
            "Sure, I’d love to help. What additional details can you share about your question?",
            "I’m here to help. Can you specify what aspect you’re curious about?",
            "Let’s explore that. What exactly would you like to know?",
            "Your question is important. Could you give me more context?",
            "I’m all ears. Could you clarify what you’re asking?",
            "I’m happy to assist with your question. Can you provide more details?"
    );

    // Fallback responses (15 variations)
    private final List<String> fallbackResponses = List.of(
            "I'm here to listen. Could you tell me more about what you're experiencing?",
            "I appreciate you sharing that. Would you like to elaborate further?",
            "Tell me more about what's on your mind.",
            "I'm here for you. Could you expand a bit on your feelings?",
            "I want to understand better. Please, tell me more.",
            "Your feelings are important. Can you share a bit more?",
            "I'm listening. Would you like to talk about it in more detail?",
            "I hear you. Please tell me more so I can help.",
            "Could you elaborate on that a bit more?",
            "I'm here to support you. Can you provide more details?",
            "Let's talk more about that. What else can you share?",
            "I care about your feelings. Tell me more about what's happening.",
            "I'm all ears. Please feel free to explain further.",
            "It sounds important. Could you describe it in more detail?",
            "I understand. Would you like to discuss this further?"
    );

    public ChatBotService() {
        // Initialize the NLP pipeline only once
        Properties props = new Properties();
        // Tokenization, sentence splitting, parsing, and sentiment analysis
        props.setProperty("annotators", "tokenize,ssplit,parse,sentiment");
        this.pipeline = new StanfordCoreNLP(props);
        this.random = new Random();
    }

    /**
     * This version of getBotReply takes a userId, so you can handle multiple users
     * or store conversation context per user if needed.
     *
     * @param userId      The ID of the user (could be a session ID, username, etc.)
     * @param userMessage The message sent by the user
     * @return A chatbot response tailored to the userMessage (and potentially userId)
     */
    public String getBotReply(String userId, String userMessage) {
        // Currently, userId is not used for context. You can store user-specific data
        // in a map or database to create a more context-aware experience.

        // Normalize the message for rule-based checks
        String normalizedMessage = userMessage.toLowerCase();

        // Annotate the user message using Stanford CoreNLP
        Annotation annotation = new Annotation(userMessage);
        pipeline.annotate(annotation);

        // Calculate sentiment for all sentences and average if multiple are present
        List<?> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        int sentimentScore = 0;
        int sentenceCount = 0;
        for (Object sentenceObj : sentences) {
            String sentenceSentiment = ((edu.stanford.nlp.util.CoreMap) sentenceObj)
                    .get(SentimentCoreAnnotations.SentimentClass.class);
            sentimentScore += sentimentToScore(sentenceSentiment);
            sentenceCount++;
        }
        // Default to neutral if no sentences are found
        String overallSentiment = "Neutral";
        if (sentenceCount > 0) {
            int averageScore = sentimentScore / sentenceCount;
            overallSentiment = scoreToSentiment(averageScore);
        }

        // Use sentiment analysis as a primary factor for emotional tone
        if (overallSentiment.equalsIgnoreCase("Very negative") || overallSentiment.equalsIgnoreCase("Negative")) {
            return "It sounds like you're feeling quite down. Sometimes sharing more about what's troubling you can help. Would you like to talk about it?";
        } else if (overallSentiment.equalsIgnoreCase("Very positive") || overallSentiment.equalsIgnoreCase("Positive")) {
            return "It's great to hear that you're feeling positive! Is there anything specific you'd like to discuss or any support you need?";
        } else {
            // Additional rule-based keyword checks for a broader range of queries
            if (normalizedMessage.contains("anxiety") || normalizedMessage.contains("nervous")) {
                return randomResponse(anxietyResponses);
            } else if (normalizedMessage.contains("stress") || normalizedMessage.contains("overwhelmed")) {
                return randomResponse(stressResponses);
            } else if (normalizedMessage.contains("depression") || normalizedMessage.contains("sad")) {
                return randomResponse(depressionResponses);
            } else if (normalizedMessage.contains("hello") || normalizedMessage.contains("hi") || normalizedMessage.contains("hey")) {
                return randomResponse(greetingResponses);
            } else if (normalizedMessage.contains("bye") || normalizedMessage.contains("goodbye")) {
                return randomResponse(goodbyeResponses);
            } else if (normalizedMessage.contains("help")) {
                return randomResponse(helpResponses);
            } else if (normalizedMessage.contains("question") || normalizedMessage.contains("inquiry")) {
                return randomResponse(questionResponses);
            } else if (normalizedMessage.endsWith("?")) {
                // If it is a question not covered above
                return randomResponse(questionResponses);
            } else {
                // Default fallback response for any other message
                return randomResponse(fallbackResponses);
            }
        }
    }

    private String randomResponse(List<String> responses) {
        return responses.get(random.nextInt(responses.size()));
    }

    // Helper method: Convert sentiment label to a numerical score
    private int sentimentToScore(String sentiment) {
        // Using a simple mapping: Very negative = 0, Negative = 1, Neutral = 2, Positive = 3, Very positive = 4
        switch (sentiment.toLowerCase()) {
            case "very negative":
                return 0;
            case "negative":
                return 1;
            case "neutral":
                return 2;
            case "positive":
                return 3;
            case "very positive":
                return 4;
            default:
                return 2; // Assume neutral if unrecognized
        }
    }

    // Helper method: Convert average numerical score back to a sentiment label
    private String scoreToSentiment(int score) {
        if (score <= 0) return "Very negative";
        else if (score == 1) return "Negative";
        else if (score == 2) return "Neutral";
        else if (score == 3) return "Positive";
        else return "Very positive";
    }
}

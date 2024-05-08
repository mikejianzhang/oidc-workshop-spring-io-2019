package com.example.library.client.web;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class BookResource {

  private UUID identifier;

  private String isbn;

  private String title;

  private String description;

  private boolean borrowed;

  private List<String> authors = new ArrayList<>();

  private User borrowedBy;

  @SuppressWarnings("unused")
  public BookResource() {}

  public UUID getIdentifier() {
    return identifier;
  }

  public void setIdentifier(UUID identifier) {
    this.identifier = identifier;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<String> getAuthors() {
    return authors;
  }

  public void setAuthors(List<String> authors) {
    this.authors = authors;
  }

  public boolean isBorrowed() {
    return borrowed;
  }

  public void setBorrowed(boolean borrowed) {
    this.borrowed = borrowed;
  }

  public User getBorrowedBy() {
    return borrowedBy;
  }

  public void setBorrowedBy(User borrowedBy) {
    this.borrowedBy = borrowedBy;
  }

  public void doBorrow(User user) {
    if (!this.borrowed) {
      this.borrowed = true;
      this.borrowedBy = user;
    }
  }

  public boolean isReturnBookAllowed() {
    if (!isBorrowed()) {
      return false;
    }

    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof OidcUser) {
      OidcUser oidcUser =
          (OidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      return borrowedBy != null && borrowedBy.getEmail().equals(oidcUser.getEmail());
    } else {
      // Always fail secure
      return false;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    BookResource bookResource = (BookResource) o;
    return borrowed == bookResource.borrowed
        && identifier.equals(bookResource.identifier)
        && isbn.equals(bookResource.isbn)
        && title.equals(bookResource.title)
        && description.equals(bookResource.description)
        && authors.equals(bookResource.authors)
        && Objects.equals(borrowedBy, bookResource.borrowedBy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        super.hashCode(), identifier, isbn, title, description, borrowed, authors, borrowedBy);
  }

  @Override
  public String toString() {
    return "BookResource{"
        + "identifier="
        + identifier
        + ", isbn='"
        + isbn
        + '\''
        + ", title='"
        + title
        + '\''
        + ", description='"
        + description
        + '\''
        + ", borrowed="
        + borrowed
        + ", authors="
        + authors
        + ", borrowedBy="
        + borrowedBy
        + '}';
  }
}
